package com.shealt.healthreport.ui.viewmodels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shealt.healthreport.data.local.ReportDao
import com.shealt.healthreport.data.local.ReportEntity
import com.shealt.healthreport.data.local.SettingsDataStore
import com.shealt.healthreport.data.repository.HealthPermissionManager
import com.shealt.healthreport.data.repository.SamsungHealthRepository
import com.shealt.healthreport.export.JsonExporter
import com.shealt.healthreport.pdf.PdfReportGenerator
import com.shealt.healthreport.worker.NotificationHelper
import com.shealt.healthreport.worker.WorkScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import com.samsung.android.sdk.health.data.error.ResolvablePlatformException
import com.samsung.android.sdk.health.data.error.HealthDataException
import com.samsung.android.sdk.health.data.error.ErrorCode

@HiltViewModel
class MainViewModel @Inject constructor(
    private val permissionManager: HealthPermissionManager,
    private val samsungHealthRepository: SamsungHealthRepository,
    private val pdfReportGenerator: PdfReportGenerator,
    private val jsonExporter: JsonExporter,
    private val reportDao: ReportDao,
    private val settingsDataStore: SettingsDataStore,
    private val workScheduler: WorkScheduler,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _hasPermissions = MutableStateFlow(false)
    val hasPermissions: StateFlow<Boolean> = _hasPermissions.asStateFlow()

    private val _isGeneratingReport = MutableStateFlow(false)
    val isGeneratingReport: StateFlow<Boolean> = _isGeneratingReport.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    val reports: StateFlow<List<ReportEntity>> = reportDao.getAllReports()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val autoReportEnabled: StateFlow<Boolean> = settingsDataStore.autoReportEnabledFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val reportHour: StateFlow<Int> = settingsDataStore.reportHourFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, 23)

    val reportMinute: StateFlow<Int> = settingsDataStore.reportMinuteFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    init {
        checkPermissions()
        viewModelScope.launch {
            try {
                if (autoReportEnabled.first()) {
                    scheduleWorker()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Init scheduleWorker error", e)
            }
        }
    }

    fun checkPermissions() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Checking permissions...")
                _hasPermissions.value = permissionManager.hasAllPermissions()
                Log.d(TAG, "Has permissions: ${_hasPermissions.value}")
            } catch (e: Exception) {
                Log.e(TAG, "checkPermissions error: ${e.message}", e)
                _hasPermissions.value = false
                _errorMessage.value = "İzin kontrolü hatası: ${e.message}"
            }
        }
    }

    fun requestPermissions(activity: Activity) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Requesting permissions...")
                _statusMessage.value = "İzin isteniyor..."
                val granted = permissionManager.requestPermissions(activity)
                _hasPermissions.value = granted
                if (granted) {
                    _statusMessage.value = "İzinler başarıyla verildi!"
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "İzinler verilmedi. Samsung Health'te Developer Mode açık olduğundan emin olun."
                    _statusMessage.value = null
                }
                Log.d(TAG, "Permissions granted: $granted")
            } catch (e: Exception) {
                Log.e(TAG, "requestPermissions error: ${e.javaClass.simpleName} - ${e.message}", e)
                _errorMessage.value = getReadableErrorMessage(e)
                _statusMessage.value = null
                _hasPermissions.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearStatus() {
        _statusMessage.value = null
    }

    fun generateReportNow() {
        viewModelScope.launch {
            _isGeneratingReport.value = true
            _errorMessage.value = null
            try {
                if (!permissionManager.hasAllPermissions()) {
                    _errorMessage.value = "Sağlık verisi izinleri verilmedi. Önce izin verin."
                    _isGeneratingReport.value = false
                    return@launch
                }

                _statusMessage.value = "Veriler çekiliyor..."
                val today = LocalDate.now()
                val report = samsungHealthRepository.getDailyReport(today)

                _statusMessage.value = "PDF oluşturuluyor..."
                val pdfFile = pdfReportGenerator.generatePdf(report)

                if (pdfFile != null) {
                    reportDao.insertReport(
                        ReportEntity(
                            dateString = today.toString(),
                            filePath = pdfFile.absolutePath,
                            createdAtTimestamp = System.currentTimeMillis(),
                            stepCount = report.steps?.total,
                            sleepScore = report.sleep?.score,
                            energyScore = report.energyScore,
                            avgHeartRate = report.heartRate?.dailySummary?.avg,
                            workoutCount = report.workouts.size,
                            sleepDurationMinutes = report.sleep?.totalMinutes
                        )
                    )
                    notificationHelper.showReportReadyNotification(pdfFile)
                    _statusMessage.value = "Rapor başarıyla oluşturuldu!"
                } else {
                    _errorMessage.value = "PDF dosyası oluşturulamadı."
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error generating report", e)
                _errorMessage.value = "Rapor oluşturma hatası: ${getReadableErrorMessage(e)}"
            } finally {
                _isGeneratingReport.value = false
            }
        }
    }

    fun exportJsonNow() {
        viewModelScope.launch {
            _isGeneratingReport.value = true
            _errorMessage.value = null
            try {
                if (!permissionManager.hasAllPermissions()) {
                    _errorMessage.value = "Sağlık verisi izinleri verilmedi. Önce izin verin."
                    _isGeneratingReport.value = false
                    return@launch
                }

                _statusMessage.value = "JSON Dışa Aktarılıyor..."
                val today = LocalDate.now()
                val report = samsungHealthRepository.getDailyReport(today)
                val file = jsonExporter.exportReport(report)

                if (file != null) {
                    _statusMessage.value = "JSON başarıyla dışa aktarıldı: ${file.name}"
                } else {
                    _errorMessage.value = "JSON dosyası oluşturulamadı."
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error exporting JSON", e)
                _errorMessage.value = "JSON dışa aktarma hatası: ${getReadableErrorMessage(e)}"
            } finally {
                _isGeneratingReport.value = false
            }
        }
    }

    fun deleteReport(report: ReportEntity) {
        viewModelScope.launch {
            try {
                // Veritabanından sil
                reportDao.deleteReport(report)
                // Fiziksel dosyayı sil
                val file = java.io.File(report.filePath)
                if (file.exists()) {
                    file.delete()
                }
                _statusMessage.value = "Rapor silindi."
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting report", e)
                _errorMessage.value = "Rapor silinemedi: ${e.message}"
            }
        }
    }

    fun setAutoReportEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setAutoReportEnabled(enabled)
            if (enabled) {
                scheduleWorker()
            } else {
                workScheduler.cancelSchedule()
            }
        }
    }

    fun setReportTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            settingsDataStore.setReportTime(hour, minute)
            if (autoReportEnabled.value) {
                scheduleWorker()
            }
        }
    }

    private suspend fun scheduleWorker() {
        val hour = settingsDataStore.reportHourFlow.first()
        val minute = settingsDataStore.reportMinuteFlow.first()
        workScheduler.scheduleNightlyJsonExport(hour, minute)
    }

    private fun getReadableErrorMessage(e: Throwable): String {
        val cause = e.cause ?: e
        return when (cause) {
            is ResolvablePlatformException -> {
                "Samsung Health bağlantı veya sürüm sorunu. Platform güncellenmeli veya ayarlanmalı."
            }
            is HealthDataException -> {
                when (cause.errorCode) {
                    ErrorCode.ERR_PLATFORM_NOT_INSTALLED -> "Samsung Health uygulaması telefonda yüklü değil."
                    ErrorCode.ERR_OLD_VERSION_PLATFORM -> "Samsung Health uygulamasının güncellenmesi gerekiyor."
                    ErrorCode.ERR_PLATFORM_DISABLED -> "Samsung Health uygulaması devre dışı bırakılmış."
                    ErrorCode.ERR_PLATFORM_NOT_INITIALIZED -> "Samsung Health henüz başlatılmadı. Lütfen uygulamayı bir kez açın."
                    ErrorCode.ERR_CONNECTION_FAIL -> "Samsung Health platformuna bağlantı kurulamadı."
                    ErrorCode.ERR_CONNECTION_TIMEOUT -> "Samsung Health bağlantı zaman aşımına uğradı."
                    ErrorCode.ERR_NO_USER_PERMISSION -> "Sağlık verilerini okumak için kullanıcı izni bulunmuyor."
                    ErrorCode.ERR_ACCESS_CONTROL -> "Erişim kontrol hatası. Samsung Health Developer Mode açık olmayabilir."
                    else -> "Samsung Health Hatası (${cause.errorCode}): ${cause.errorMessage ?: cause.message}"
                }
            }
            else -> "Beklenmeyen hata: ${cause.javaClass.simpleName}\n${cause.message}"
        }
    }

    fun exportDateRange(startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            _isGeneratingReport.value = true
            _errorMessage.value = null
            try {
                if (!permissionManager.hasAllPermissions()) {
                    _errorMessage.value = "Sağlık verisi izinleri verilmedi. Önce izin verin."
                    _isGeneratingReport.value = false
                    return@launch
                }

                _statusMessage.value = "Çoklu gün verileri çekiliyor..."
                val reports = mutableListOf<com.shealt.healthreport.data.model.DailyHealthReport>()
                var currentDate = startDate
                
                while (!currentDate.isAfter(endDate)) {
                    val report = samsungHealthRepository.getDailyReport(currentDate)
                    reports.add(report)
                    currentDate = currentDate.plusDays(1)
                }
                
                _statusMessage.value = "JSON Dışa Aktarılıyor..."
                val file = jsonExporter.exportMultipleReports(reports, startDate, endDate)

                if (file != null) {
                    _statusMessage.value = "JSON başarıyla dışa aktarıldı: ${file.name}"
                } else {
                    _errorMessage.value = "JSON dosyası oluşturulamadı."
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error exporting JSON range", e)
                _errorMessage.value = "JSON dışa aktarma hatası: ${getReadableErrorMessage(e)}"
            } finally {
                _isGeneratingReport.value = false
            }
        }
    }
}

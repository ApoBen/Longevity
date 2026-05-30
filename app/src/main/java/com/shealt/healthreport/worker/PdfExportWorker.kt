package com.shealt.healthreport.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shealt.healthreport.data.repository.HealthPermissionManager
import com.shealt.healthreport.data.repository.SamsungHealthRepository
import com.shealt.healthreport.pdf.PdfReportGenerator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

@HiltWorker
class PdfExportWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val samsungHealthRepository: SamsungHealthRepository,
    private val permissionManager: HealthPermissionManager,
    private val pdfReportGenerator: PdfReportGenerator,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            if (!permissionManager.hasAllPermissions()) {
                return Result.failure()
            }

            val today = LocalDate.now()
            val report = samsungHealthRepository.getDailyReport(today)
            val file = pdfReportGenerator.generateMultipleDaysPdf(listOf(report), today, today)

            if (file != null) {
                Log.d("PdfExportWorker", "PDF Export successful: ${file.absolutePath}")
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}

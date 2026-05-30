package com.shealt.healthreport.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shealt.healthreport.data.local.ReportDao
import com.shealt.healthreport.data.local.ReportEntity
import com.shealt.healthreport.data.repository.HealthPermissionManager
import com.shealt.healthreport.data.repository.SamsungHealthRepository
import com.shealt.healthreport.pdf.PdfReportGenerator
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

@HiltWorker
class HealthDataWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val samsungHealthRepository: SamsungHealthRepository,
    private val permissionManager: HealthPermissionManager,
    private val pdfReportGenerator: PdfReportGenerator,
    private val reportDao: ReportDao,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            if (!permissionManager.hasAllPermissions()) {
                return Result.failure()
            }

            val today = LocalDate.now()
            val report = samsungHealthRepository.getDailyReport(today)
            val pdfFile = pdfReportGenerator.generatePdf(report)

            if (pdfFile != null) {
                // Save to database
                reportDao.insertReport(
                    ReportEntity(
                        dateString = today.toString(),
                        filePath = pdfFile.absolutePath,
                        createdAtTimestamp = System.currentTimeMillis(),
                        stepCount = report.steps?.totalSteps,
                        sleepScore = report.sleep?.sleepScore,
                        energyScore = report.energy?.score,
                        avgHeartRate = report.heartRate?.averageBpm,
                        workoutCount = report.workouts.size,
                        sleepDurationMinutes = report.sleep?.totalDurationMinutes
                    )
                )

                // Show notification
                notificationHelper.showReportReadyNotification(pdfFile)

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

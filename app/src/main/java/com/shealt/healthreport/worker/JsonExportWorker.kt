package com.shealt.healthreport.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shealt.healthreport.data.repository.HealthPermissionManager
import com.shealt.healthreport.data.repository.SamsungHealthRepository
import com.shealt.healthreport.export.JsonExporter
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

@HiltWorker
class JsonExportWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val samsungHealthRepository: SamsungHealthRepository,
    private val permissionManager: HealthPermissionManager,
    private val jsonExporter: JsonExporter,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            if (!permissionManager.hasAllPermissions()) {
                return Result.failure()
            }

            val today = LocalDate.now()
            val report = samsungHealthRepository.getDailyReport(today)
            val file = jsonExporter.exportReport(report)

            if (file != null) {
                Log.d("JsonExportWorker", "JSON Export successful: ${file.absolutePath}")
                // Optionally show a notification that export is done
                // notificationHelper.showJsonExportNotification(file)
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

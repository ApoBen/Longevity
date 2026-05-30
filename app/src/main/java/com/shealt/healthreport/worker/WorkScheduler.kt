package com.shealt.healthreport.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun scheduleNightlyJsonExport(hour: Int = 23, minute: Int = 0) {
        val now = LocalDateTime.now()
        var targetTime = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)

        if (now.isAfter(targetTime)) {
            targetTime = targetTime.plusDays(1)
        }

        val initialDelay = Duration.between(now, targetTime).toMillis()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val exportRequest = PeriodicWorkRequestBuilder<JsonExportWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "NightlyJsonExport",
            ExistingPeriodicWorkPolicy.UPDATE,
            exportRequest
        )
    }

    fun cancelSchedule() {
        WorkManager.getInstance(context).cancelUniqueWork("NightlyJsonExport")
        WorkManager.getInstance(context).cancelUniqueWork("DailyHealthReportWork")
        WorkManager.getInstance(context).cancelAllWork()
    }
}

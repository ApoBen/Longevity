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
    fun scheduleDailyReport(hour: Int = 20, minute: Int = 0) {
        // Kullanıcı isteği üzerine arka plan işlemleri tamamen iptal edilmiştir.
        cancelSchedule()
    }

    fun cancelSchedule() {
        WorkManager.getInstance(context).cancelUniqueWork("DailyHealthReportWork")
        WorkManager.getInstance(context).cancelAllWork()
    }
}

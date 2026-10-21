package com.b3insights.app.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ValuationMonitor {
    private const val WORK_NAME = "valuation_check"

    fun schedule(context: Context) {
        ValuationNotifier.ensureChannel(context)
        val req = PeriodicWorkRequestBuilder<ValuationCheckWorker>(5, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE, // aplica intervalo de 5h
            req
        )
    }

    fun cancel(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}

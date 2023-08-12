package com.example.tracker.bg.work

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class UploadWorkScheduler(private var context: Context) : WorkScheduler {

    companion object {
        const val REPEAT_WORK_INTERVAL = 1L
    }

    override fun scheduleSync() {
        val requestSendLocation = PeriodicWorkRequestBuilder<UploadLocationsWork>(
            repeatInterval = REPEAT_WORK_INTERVAL,
            repeatIntervalTimeUnit = TimeUnit.SECONDS
        ).build()
        WorkManager.getInstance(context)
            .enqueue(requestSendLocation)
        Log.d("TAGG", "Start worker send location")
    }

}

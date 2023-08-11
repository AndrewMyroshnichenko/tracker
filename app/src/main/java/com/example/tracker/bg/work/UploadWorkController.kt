package com.example.tracker.bg.work

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class UploadWorkController(var context: Context) : WorkController {

    companion object {
        const val REPEAT_WORK_INTERVAL = 5L
    }

    override fun startWorkerSendLocation() {
        val requestSendLocation = PeriodicWorkRequestBuilder<UploadLocationsWork>(
            repeatInterval = REPEAT_WORK_INTERVAL,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(context)
            .enqueue(requestSendLocation)
        Log.d("TAGG", "Start worker send location")
    }

}
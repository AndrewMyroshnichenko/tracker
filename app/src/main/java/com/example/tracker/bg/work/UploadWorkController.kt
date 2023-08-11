package com.example.tracker.bg.work

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class UploadWorkController(var context: Context) : WorkController {

    override fun startWorkerSendLocation() {
        val requestSendLocation = PeriodicWorkRequestBuilder<UploadLocationsWork>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).build()
        WorkManager.getInstance(context)
            .enqueue(requestSendLocation)
        Log.d("TAGG", "Start worker send location")
    }
}
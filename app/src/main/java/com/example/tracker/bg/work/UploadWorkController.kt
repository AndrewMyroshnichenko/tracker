package com.example.tracker.bg.work

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class UploadWorkController(var context: Context) : WorkController {

    override fun startWorkerSendLocation() {
        val requestSendLocation = PeriodicWorkRequestBuilder<UploadLocationsWork>(
            repeatInterval = 5,
            repeatIntervalTimeUnit = TimeUnit.SECONDS
        ).build()
        WorkManager.getInstance(context)
            .enqueue(requestSendLocation)
        Log.d("TAGG", "Start worker send location")
    }
}
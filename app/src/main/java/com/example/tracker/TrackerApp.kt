package com.example.tracker

import android.annotation.SuppressLint
import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tracker.bg.work.UploadLocationsWork
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class TrackerApp : Application(), Configuration.Provider {


    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        startWorkerSendLocation()
    }

    @SuppressLint("RestrictedApi")
    private fun startWorkerSendLocation() {
        val requestSendLocation = PeriodicWorkRequestBuilder<UploadLocationsWork>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).build()
        WorkManager.getInstance(applicationContext)
            .enqueue(requestSendLocation)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}
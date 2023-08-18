package com.example.tracker.bg

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.tracker.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    @Inject
    lateinit var controller: LocationController

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                TRACKER_CHANNEL_ID,
                TRACKER_CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }

    private fun start() {
        val notification: Notification = NotificationCompat.Builder(this, TRACKER_CHANNEL_ID)
            .setContentTitle("tracker")
            .setContentText("Tracking your location...")
            .build()
        startForeground(TRACKER_NOTIFICATION_ID, notification)
        controller.onCreate()
    }

    private fun stop() {
        stopSelf()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val TRACKER_CHANNEL_ID = "TRACKER_CHANNEL_ID"
        const val TRACKER_NOTIFICATION_ID = 1
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

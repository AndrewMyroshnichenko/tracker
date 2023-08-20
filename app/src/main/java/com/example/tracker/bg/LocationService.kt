package com.example.tracker.bg

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
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
            START -> start()
            STOP -> stop()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }

    private fun start() {
        val notification: Notification = NotificationCompat.Builder(this, TRACKER_CHANNEL_ID)
            .setContentTitle(getString(R.string.tracker))
            .setContentText(getString(R.string.collects_locations))
            .setSmallIcon(R.drawable.img_tracker_collects_locations)
            .addAction(R.drawable.ic_stop, getString(R.string.stop), startStopIntent(STOP))
            .addAction(R.drawable.ic_start, getString(R.string.start), startStopIntent(START))
            .build()
        startForeground(TRACKER_NOTIFICATION_ID, notification)
        controller.onCreate()
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_DETACH)
        stopSelf()
    }

    private fun startStopIntent(action: String): PendingIntent {
        val intent = Intent(this, LocationService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    companion object {
        const val START = "ACTION_START"
        const val STOP = "ACTION_STOP"
        const val TRACKER_CHANNEL_ID = "TRACKER_CHANNEL_ID"
        const val TRACKER_NOTIFICATION_ID = 1
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

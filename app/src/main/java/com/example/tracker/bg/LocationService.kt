package com.example.tracker.bg

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    @Inject
    lateinit var controller: LocationController

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("GET_MARKS", "onStartCommand")
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
        controller.onCreate()
    }

    private fun stop() {
        stopSelf()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

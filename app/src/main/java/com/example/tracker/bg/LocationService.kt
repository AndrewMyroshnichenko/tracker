package com.example.tracker.bg

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.IBinder
import android.util.Log
import com.example.tracker.models.gps.DefaultLocationSource
import com.example.tracker.models.gps.LocationServiceController
import com.example.tracker.models.gps.LocationServiceInterface
import com.example.tracker.models.gps.LocationSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocationService() : Service() {

    @Inject
    lateinit var controller: LocationServiceInterface

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
        controller.destroy()
    }

    private fun start() {
        controller.getLocationUpdates()
    }

    private fun stop() {
        controller.stop()
        stopSelf()
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

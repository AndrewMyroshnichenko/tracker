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
import com.example.tracker.models.gps.LocationSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService : Service() {

    private lateinit var locationSource: LocationSource

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        locationSource = DefaultLocationSource(applicationContext)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("GET_MARKS", "onStartCommand")
        when (intent?.action) {
            ACTION_START -> {
                start()
                LocationServiceController.setGpsStatus(isGpsOn(applicationContext))
            }
            ACTION_STOP -> stop()
        }
        return START_NOT_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun start() {
        Log.d("GET_MARKS", "START SERVICE")
        locationSource.getLocationUpdates()
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                Log.d(
                    "GET_MARKS",
                    "MARK: ${location.time}, ${location.longitude}, ${location.latitude}"
                )
            }.launchIn(serviceScope)
    }

    private fun stop() {
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

        fun isGpsOn(context: Context): Boolean {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

package com.example.tracker.models.gps

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationServiceController(
    val location: DefaultLocationSource,
    val locationStatus: StatusManager,
    val locationManager: LocationManager,
) : LocationServiceInterface {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

        }

        override fun onProviderDisabled(provider: String) {
            locationStatus.setGpsStatus(false)
            Log.d("TAG", "controller false")
        }

        override fun onProviderEnabled(provider: String) {
            locationStatus.setGpsStatus(true)
            Log.d("TAG", "controller true")
        }
    }

    override fun getLocationUpdates() {
        locationStatus.setServiceStatus(true)
        //locationStatus.setGpsStatus(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        Log.d("GET_MARKS", "START SERVICE")
        location.getLocationUpdates()
            .catch { e -> e.printStackTrace() }
            .onEach {
                Log.d("GET_MARKS", "MARK: ${it.time}, ${it.longitude}, ${it.latitude}")
            }.launchIn(serviceScope)
    }

    override fun onCreate(){
        locationStatus.setGpsStatus(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            try {
                Log.d("TAG", "GPS provider is enabled")
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
            } catch (e: SecurityException) {
                Log.e("TAG", "Error: ${e.message}")
            }
    }

    override fun stop() {
        locationStatus.setServiceStatus(false)
    }

    override fun destroy() {
        serviceScope.cancel()
        locationManager.removeUpdates(locationListener)
    }

}

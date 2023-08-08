package com.example.tracker.models.gps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import com.example.tracker.utils.CheckPermissions
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationSource(
    private val context: Context,
    private val locationStatus: StatusManager,
    private val locationManager: LocationManager
) : LocationSource {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(): Flow<Location> {
        return callbackFlow {

            if (!CheckPermissions.hasLocationPermission(context)) {
                throw LocationSource.LocationException("Missing location permission")
            }

            locationStatus.setGpsStatus(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))

            val locationListener: LocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    launch { send(location) }
                }

                override fun onProviderDisabled(provider: String) {
                    locationStatus.setGpsStatus(false)
                }

                override fun onProviderEnabled(provider: String) {
                    locationStatus.setGpsStatus(true)
                }
            }

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L,
                10.0f,
                locationListener,
                Looper.getMainLooper()
            )

            awaitClose {
                locationManager.removeUpdates(locationListener)
            }
        }
    }


}

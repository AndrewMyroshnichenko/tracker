package com.example.tracker.bg

import android.util.Log
import com.example.tracker.models.bus.StatusManager
import com.example.tracker.models.gps.DefaultLocationSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationServiceController(
    private val location: DefaultLocationSource,
    private val locationStatus: StatusManager,
) : LocationController {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun startLocationUpdates() {
        locationStatus.setServiceStatus(true)
        location.getLocationUpdates()
            .catch { e -> e.printStackTrace() }
            .onEach {
                Log.d("GET_MARKS", "MARK: ${it.time}, ${it.longitude}, ${it.latitude}")
            }.launchIn(serviceScope)
    }



    override fun stopLocationUpdates() {
        locationStatus.setServiceStatus(false)
    }

    override fun destroy() {
        serviceScope.cancel()
    }
}

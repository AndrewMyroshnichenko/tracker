package com.example.tracker.models.gps
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
) : LocationServiceInterface {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun getLocationUpdates() {
        locationStatus.setServiceStatus(true)
        Log.d("GET_MARKS", "START SERVICE")
        location.getLocationUpdates()
            .catch { e -> e.printStackTrace() }
            .onEach {
                Log.d("GET_MARKS", "MARK: ${it.time}, ${it.longitude}, ${it.latitude}")
            }.launchIn(serviceScope)
    }



    override fun stop() {
        locationStatus.setServiceStatus(false)
    }

    override fun destroy() {
        serviceScope.cancel()
    }

}

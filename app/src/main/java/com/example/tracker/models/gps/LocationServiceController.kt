package com.example.tracker.models.gps

import android.util.Log
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LocationServiceController : LocationServiceInterface {

    @Inject
    lateinit var location: DefaultLocationSource

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun getLocationUpdates() {
        LocationModel.setServiceStatus(true)
        Log.d("GET_MARKS", "START SERVICE")
        location.getLocationUpdates()
            .catch { e -> e.printStackTrace() }
            .onEach {
                Log.d("GET_MARKS", "MARK: ${it.time}, ${it.longitude}, ${it.latitude}")
            }.launchIn(serviceScope)
    }

    override fun stop() {
        LocationModel.setServiceStatus(false)
    }

    override fun destroy() {
        serviceScope.cancel()
    }
}

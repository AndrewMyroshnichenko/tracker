package com.example.tracker.bg

import android.util.Log
import com.example.tracker.data.auth.RoomAuthRepository
import com.example.tracker.data.locations.RoomLocationsRepository
import com.example.tracker.models.bus.StatusManager
import com.example.tracker.models.gps.DefaultLocationSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LocationServiceController(
    private val location: DefaultLocationSource,
    private val gpsStateCache: StatusManager,
) : LocationController {

    @Inject
    lateinit var repository: RoomLocationsRepository

    @Inject
    lateinit var userRepository: RoomAuthRepository



    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        gpsStateCache.setServiceStatus(true)


        location.observeLocations()
            .catch { e -> e.printStackTrace() }
            .onEach {
                Log.d("GET_MARKS", "MARK: ${it.time}, ${it.longitude}, ${it.latitude}")
            }.launchIn(serviceScope)

        location.getGpsStatusFlow()
            .catch { e -> e.printStackTrace() }
            .onEach { gpsStateCache.setGpsStatus(it) }
            .launchIn(serviceScope)
    }

    override fun onDestroy() {
        gpsStateCache.setServiceStatus(false)
        serviceScope.cancel()
    }
}

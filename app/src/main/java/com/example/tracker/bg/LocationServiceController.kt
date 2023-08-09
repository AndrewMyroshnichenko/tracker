package com.example.tracker.bg

import android.util.Log
import com.example.tracker.data.auth.RoomAuthRepository
import com.example.tracker.data.auth.User
import com.example.tracker.data.locations.Location
import com.example.tracker.data.locations.RoomLocationsRepository
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.bus.StatusManager
import com.example.tracker.models.gps.DefaultLocationSource
import com.example.tracker.models.remotedb.RemoteDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationServiceController(
    private val location: DefaultLocationSource,
    private val gpsStateCache: StatusManager,
) : LocationController {

    @Inject
    lateinit var locationRepository: RoomLocationsRepository

    @Inject
    lateinit var userRepository: RoomAuthRepository

    @Inject
    lateinit var remoteDb: RemoteDb

    @Inject
    lateinit var authNetwork: Auth

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        gpsStateCache.setServiceStatus(true)

        GlobalScope.launch {
            userRepository.updateCurrentUser(
                User(userEmail = authNetwork.getCurrentUserEmail(), isTrackingOn = "yes")
            )
        }

        location.observeLocations()
            .catch { e -> e.printStackTrace() }
            .onEach {
                val location = Location(
                    it.time.toString(),
                    authNetwork.getCurrentUserEmail(),
                    it.latitude.toString(),
                    it.longitude.toString()
                )

                GlobalScope.launch {
                    val insertDeferred = async { locationRepository.insertMark(location) }
                    insertDeferred.await()
                    try {
                        remoteDb.addLocation(location)
                        locationRepository.deleteMark(location)
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }

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
        GlobalScope.launch {
            userRepository.updateCurrentUser(
                User(userEmail = authNetwork.getCurrentUserEmail(), isTrackingOn = "no")
            )
        }
    }
}

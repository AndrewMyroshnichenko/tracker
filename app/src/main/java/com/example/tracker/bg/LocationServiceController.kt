package com.example.tracker.bg

import com.example.tracker.bg.work.WorkController
import com.example.tracker.models.bus.StatusManager
import com.example.tracker.models.gps.LocationSource
import com.example.tracker.models.locations.LocationsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationServiceController(
    private val location: LocationSource,
    private val gpsStateCache: StatusManager,
    private val locationRepository: LocationsRepository,
    private val uploadWorkController: WorkController
) : LocationController {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        gpsStateCache.setServiceStatus(true)

        location.observeLocations()
            .catch { e -> e.printStackTrace() }
            .onEach {
                if (!locationRepository.insertLocation(it)) {
                    uploadWorkController.startWorkerSendLocation()
                }
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

    companion object {
        const val TRACKING_ON = "yes"
        const val TRACKING_OFF = "no"
    }
}

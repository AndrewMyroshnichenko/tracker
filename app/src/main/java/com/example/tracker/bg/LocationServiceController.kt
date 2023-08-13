package com.example.tracker.bg

import com.example.tracker.bg.work.WorkScheduler
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
    private val uploadWorkScheduler: WorkScheduler
) : LocationController {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        gpsStateCache.setServiceStatus(true)

        location.observeLocations()
            .catch { e -> e.printStackTrace() }
            .onEach {
                try {
                    locationRepository.saveLocation(it)
                    locationRepository.syncTrackerLocations()
                } catch (e: Exception) {
                    e.printStackTrace()
                    uploadWorkScheduler.scheduleSync()
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
}

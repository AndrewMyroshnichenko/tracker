package com.example.tracker.models.locations

import com.example.tracker.models.auth.Auth
import com.example.tracker.models.locations.dao.MapLocationEntity
import com.example.tracker.models.locations.dao.MapLocationsDao
import com.example.tracker.models.locations.dao.TrackerLocationEntity
import com.example.tracker.models.locations.dao.TrackerLocationsDao
import com.example.tracker.models.locations.network.LocationsNetwork
import java.lang.Exception

class LocationsRepositoryImp(
    private val trackerDao: TrackerLocationsDao,
    private val mapDao: MapLocationsDao,
    private val network: LocationsNetwork,
    private val auth: Auth
) : LocationsRepository {

    override suspend fun saveLocation(location: Location) {
        val locationWithOwner = location.copy(ownerId = auth.getCurrentUserId())
        trackerDao.upsertLocation(TrackerLocationEntity.toLocationEntity(locationWithOwner))
    }

    override suspend fun syncTrackerLocations() {
        val locationsList = getTrackerLocalLocations()
        if (locationsList.isNotEmpty()) {
            locationsList.forEach {
                network.uploadLocation(it)
                trackerDao.deleteLocation(TrackerLocationEntity.toLocationEntity(it))
            }
        }
    }

    override suspend fun getMapLocations(): List<Location> {
        try {
            downloadMapLocations()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mapDao.getLocations(auth.getCurrentUserId()).map { it.toLocation() }
    }

    private suspend fun downloadMapLocations() {
        network.downloadLocations(auth.getCurrentUserId()).forEach {
            mapDao.upsertLocation(MapLocationEntity.toLocationEntity(it))
        }
    }

    private suspend fun getTrackerLocalLocations() =
        trackerDao.getLocations(auth.getCurrentUserId()).map { it.toLocation() }

}

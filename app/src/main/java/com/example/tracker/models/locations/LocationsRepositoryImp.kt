package com.example.tracker.models.locations

import com.example.tracker.models.auth.Auth
import com.example.tracker.models.locations.dao.MapLocationEntity
import com.example.tracker.models.locations.dao.MapLocationsDao
import com.example.tracker.models.locations.dao.TrackerLocationEntity
import com.example.tracker.models.locations.dao.TrackerLocationsDao
import com.example.tracker.models.locations.network.LocationsNetwork

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
        downloadMapLocations()
        return mapDao.getLocations(auth.getCurrentUserId()).map { it.toLocation() }
    }

    private suspend fun downloadMapLocations() {
        val list = network.downloadLocations(auth.getCurrentUserId())
            .map { MapLocationEntity.toLocationEntity(it) }
        mapDao.saveLocations(list)
    }

    private suspend fun getTrackerLocalLocations() =
        trackerDao.getLocations(auth.getCurrentUserId()).map { it.toLocation() }

}

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

    private var lastLocationTime = 0L

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

    override suspend fun getMapLocations(startDate: Long, endDate: Long): List<Location> {
        if (!isTheRequestedDataStoredLocally(endDate)){
            lastLocationTime = getLastLocationTimeOrZero()
            downloadMapLocations(lastLocationTime)
        }
        return mapDao.getLocations().map { it.toLocation() }
            .toMutableList()
            .filter { it.time.toLong() in startDate..endDate }
    }

    override suspend fun clearLocations() {
        trackerDao.deleteAllLocations()
        mapDao.deleteAllLocations()
    }

    private fun isTheRequestedDataStoredLocally(endDate: Long): Boolean{
        if (lastLocationTime > endDate) {
            return true
        }
        return false
    }

    private suspend fun getLastLocationTimeOrZero(): Long {
        return mapDao.getLocations().maxByOrNull { it.time.toLong() }?.time?.toLong() ?: 0L
    }

    private suspend fun downloadMapLocations(lastLocationTime: Long) {
        val list = network.downloadLocations(auth.getCurrentUserId(), lastLocationTime)
            .map { MapLocationEntity.toLocationEntity(it) }
        mapDao.saveLocations(list)
    }

    private suspend fun getTrackerLocalLocations() =
        trackerDao.getLocations().map { it.toLocation() }

}

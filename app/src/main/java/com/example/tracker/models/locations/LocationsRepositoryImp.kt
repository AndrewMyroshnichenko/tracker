package com.example.tracker.models.locations

import com.example.models.auth.Auth
import com.example.models.locations.cache.LocationsCache
import com.example.tracker.models.locations.dao.MapLocationEntity
import com.example.tracker.models.locations.dao.MapLocationsDao
import com.example.tracker.models.locations.dao.TrackerLocationEntity
import com.example.tracker.models.locations.dao.TrackerLocationsDao
import com.example.models.locations.network.LocationsNetwork

class LocationsRepositoryImp(
    private val trackerDao: TrackerLocationsDao,
    private val mapDao: MapLocationsDao,
    private val network: LocationsNetwork,
    private val auth: Auth,
    private val cache: LocationsCache
) : LocationsRepository {

    override suspend fun saveLocation(location: com.example.models.locations.Location) {
        val locationWithOwner = location.copy(ownerId = auth.getCurrentUserId())
        trackerDao.upsertLocation(TrackerLocationEntity.toLocationEntity(locationWithOwner))
    }

    override suspend fun syncTrackerLocations() {
        val locationsList = getTrackerLocations()
        if (locationsList.isNotEmpty()) {
            locationsList.forEach {
                network.uploadLocation(it)
                trackerDao.deleteLocation(TrackerLocationEntity.toLocationEntity(it))
            }
        }
    }

    override suspend fun getMapLocations(startDate: Long, endDate: Long): List<com.example.models.locations.Location> {
        val result = cache.getLocations(startDate, endDate)
        if (result.locations.isNotEmpty()) {
            return result.locations
        }
        if (result.loaded) {
            val list = mapDao.getLocations(startDate, endDate).map { it.toLocation() }
            cache.putLocations(list, startDate, endDate)
            return list
        }
        val list = network.downloadLocations(auth.getCurrentUserId(), startDate, endDate)
        mapDao.saveLocations(list.map { MapLocationEntity.toLocationEntity(it) })
        cache.putLocations(list, startDate, endDate)
        return list
    }

    override suspend fun getTrackerLocations() = trackerDao.getLocations().map { it.toLocation() }

    override suspend fun clearLocations() {
        trackerDao.deleteAllLocations()
        mapDao.deleteAllLocations()
        cache.clear()
    }
}

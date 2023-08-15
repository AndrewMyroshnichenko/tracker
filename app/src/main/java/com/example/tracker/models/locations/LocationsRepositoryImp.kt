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

    private var locationsCash: MutableList<Location>? = null

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
        if (locationsCash == null) {
            locationsCash = mapDao.getLocations().map { it.toLocation() }.toMutableList()
        }

        val list: MutableList<Location> = locationsCash as MutableList<Location>
        val firstLocation = list.minByOrNull { it.time.toLong() }?.time?.toLong() ?: 0L
        val lastLocation = list.maxByOrNull { it.time.toLong() }?.time?.toLong() ?: 0L

        if (list.isEmpty() || firstLocation > startDate || lastLocation < endDate) {
            downloadMapLocations(lastLocation)
        } else {
            list.filter { it.time.toLong() in startDate..endDate }
        }
        return list
    }

    override suspend fun clearLocations() {
        trackerDao.deleteAllLocations()
        mapDao.deleteAllLocations()
    }

    private suspend fun downloadMapLocations(lastLocationTime: Long) {
        val list = network.downloadLocations(auth.getCurrentUserId(), lastLocationTime)
            .map { MapLocationEntity.toLocationEntity(it) }
        mapDao.saveLocations(list)
    }

    private suspend fun getTrackerLocalLocations() =
        trackerDao.getLocations().map { it.toLocation() }

}

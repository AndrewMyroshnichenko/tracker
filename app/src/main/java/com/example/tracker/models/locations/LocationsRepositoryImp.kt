package com.example.tracker.models.locations

import com.example.tracker.models.auth.Auth
import com.example.tracker.models.locations.dao.LocationEntity
import com.example.tracker.models.locations.dao.LocationsDao
import com.example.tracker.models.locations.network.LocationsNetwork

class LocationsRepositoryImp(
    private val dao: LocationsDao,
    private val network: LocationsNetwork,
    private val auth: Auth
) : LocationsRepository {

    override suspend fun saveLocation(location: Location) {
        val locationWithOwner = location.copy(ownerId = auth.getCurrentUserId())
        dao.upsertLocation(LocationEntity.toLocationEntity(locationWithOwner))
    }

    override suspend fun syncTrackerLocations() {
        val locationsList = getLocations()
        if (locationsList.isNotEmpty()) {
            locationsList.forEach {
                network.uploadLocation(it)
                dao.deleteLocation(LocationEntity.toLocationEntity(it))
            }
        }
    }

    private suspend fun getLocations() =
        dao.getMarks(auth.getCurrentUserId()).map { it.toLocation() }

}

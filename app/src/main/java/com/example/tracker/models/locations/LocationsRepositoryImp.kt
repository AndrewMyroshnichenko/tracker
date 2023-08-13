package com.example.tracker.models.locations

import com.example.tracker.models.auth.Auth
import com.example.tracker.models.locations.dao.LocationEntity
import com.example.tracker.models.locations.dao.LocationsDao
import com.example.tracker.models.locations.network.LocationsNetwork

class LocationsRepositoryImp(
    private val dao: LocationsDao,
    private val network: LocationsNetwork,
    private val auth: Auth
) :
    LocationsRepository {

    override suspend fun saveLocation(
        location: Location
    ) {
        val locationWithOwner = location.copy(ownerId = auth.getCurrentUserId())
        dao.upsertMark(LocationEntity.toLocationEntity(locationWithOwner))
        network.uploadLocation(locationWithOwner)
        dao.deleteMark(LocationEntity.toLocationEntity(locationWithOwner))
    }

    override suspend fun getLocations() = dao.getMarks(auth.getCurrentUserId()).map { it.toLocation() }

}

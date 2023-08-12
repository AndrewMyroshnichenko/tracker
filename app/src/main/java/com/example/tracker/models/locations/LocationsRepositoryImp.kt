package com.example.tracker.models.locations

import com.example.tracker.models.locations.dao.LocationEntity
import com.example.tracker.models.locations.dao.LocationsDao
import com.example.tracker.models.locations.network.LocationsNetwork

class LocationsRepositoryImp(private val dao: LocationsDao, private val network: LocationsNetwork) :
    LocationsRepository {

    override suspend fun saveLocation(
        location: Location
    ) {
        dao.upsertMark(LocationEntity.toLocationEntity(location))
        network.uploadLocation(location)
        dao.deleteMark(LocationEntity.toLocationEntity(location))
    }

    override suspend fun getLocations() = dao.getMarks().map { it.toLocation() }

}

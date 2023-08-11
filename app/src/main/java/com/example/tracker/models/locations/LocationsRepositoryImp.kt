package com.example.tracker.models.locations

import com.example.tracker.models.locations.dao.LocationEntity
import com.example.tracker.models.locations.dao.LocationsDao
import com.example.tracker.models.locations.network.LocationsNetwork

class LocationsRepositoryImp(val dao: LocationsDao, val network: LocationsNetwork) :
    LocationsRepository {

    override suspend fun uploadLocation(location: Location) {
        network.uploadLocation(location)
    }

    override suspend fun insertLocation(
        location: Location
    ) = dao.insertMark(LocationEntity.toLocationEntity(location))

    override suspend fun deleteLocation(
        location: Location
    ) = dao.deleteMark(LocationEntity.toLocationEntity(location))

    override suspend fun getLocations() = dao.getMarks().map { it.toLocation() }

}

package com.example.tracker.models.locations

import com.example.tracker.models.locations.dao.LocationDbEntity
import com.example.tracker.models.locations.dao.LocationsDao
import com.example.tracker.models.locations.network.LocationsNetwork

class LocationsRepositoryImp(val dao: LocationsDao, val network: LocationsNetwork) :
    LocationsRepository {

    override suspend fun insertLocation(location: Location): Boolean {
        try {
            network.uploadLocation(location)
            dao.deleteMark(LocationDbEntity.toLocationDbEntity(location))
        } catch (e: Exception) {
            dao.insertMark(LocationDbEntity.toLocationDbEntity(location))
            return false
        }
        return true
    }

    override suspend fun deleteMark(mark: Location) =
        dao.deleteMark(LocationDbEntity.toLocationDbEntity(mark))

    override suspend fun getMarks() = dao.getMarks().map { it.toLocation() }

}

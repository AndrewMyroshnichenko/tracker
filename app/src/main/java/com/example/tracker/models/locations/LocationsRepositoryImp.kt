package com.example.tracker.models.locations

import com.example.tracker.models.locations.dao.LocationDbEntity
import com.example.tracker.models.locations.dao.LocationsDao

class LocationsRepositoryImp(val dao: LocationsDao) : LocationsRepository {

    override suspend fun insertMark(mark: Location) =
        dao.insertMark(LocationDbEntity.toLocationDbEntity(mark))

    override suspend fun deleteMark(mark: Location) =
        dao.deleteMark(LocationDbEntity.toLocationDbEntity(mark))

    override suspend fun getMarks(email: String) = dao.getMarks(email).map { it.toLocation() }

}

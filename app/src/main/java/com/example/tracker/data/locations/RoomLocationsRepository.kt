package com.example.tracker.data.locations

import com.example.tracker.data.locations.dao.LocationDbEntity
import com.example.tracker.data.locations.dao.LocationsDao
import javax.inject.Inject

class RoomLocationsRepository @Inject constructor(private val dao: LocationsDao) :
    LocationsRepository {

    override suspend fun insertMark(mark: Location) =
        dao.insertMark(LocationDbEntity.toMarkDbEntity(mark))

    override suspend fun deleteMark(mark: Location) =
        dao.deleteMark(LocationDbEntity.toMarkDbEntity(mark))

    override suspend fun getMarks(email: String) = dao.getMarks(email).map { it.toMark() }

}
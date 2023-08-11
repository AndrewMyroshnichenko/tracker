package com.example.tracker.models.locations

interface LocationsRepository {
    suspend fun insertLocation(mark: Location): Boolean

    suspend fun deleteMark(mark: Location)

    suspend fun getMarks(): List<Location>
}
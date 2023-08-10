package com.example.tracker.models.locations

interface LocationsRepository {
    suspend fun insertMark(mark: Location)

    suspend fun deleteMark(mark: Location)

    suspend fun getMarks(email: String): List<Location>
}
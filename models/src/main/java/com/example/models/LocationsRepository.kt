package com.example.models

interface LocationsRepository {

    suspend fun saveLocation(location: com.example.models.locations.Location)

    suspend fun syncTrackerLocations()

    suspend fun getMapLocations(
        startDate: Long, endDate: Long
    ): List<com.example.models.locations.Location>

    suspend fun getTrackerLocations(): List<com.example.models.locations.Location>

    suspend fun clearLocations()
}

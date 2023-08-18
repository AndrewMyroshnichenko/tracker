package com.example.tracker.models.locations

interface LocationsRepository {

    suspend fun saveLocation(location: Location)

    suspend fun syncTrackerLocations()

    suspend fun getMapLocations(startDate: Long, endDate: Long): List<Location>

    suspend fun getTrackerLocations(): List<Location>

    suspend fun clearLocations()



}

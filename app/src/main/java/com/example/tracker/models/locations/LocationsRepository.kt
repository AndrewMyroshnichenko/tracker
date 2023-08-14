package com.example.tracker.models.locations

interface LocationsRepository {

    suspend fun saveLocation(location: Location)

    suspend fun syncTrackerLocations()

    suspend fun getMapLocations(lastLocationTime: Long): List<Location>

    suspend fun clearLocations()

}

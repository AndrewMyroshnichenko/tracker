package com.example.tracker.models.locations

interface LocationsRepository {

    suspend fun uploadLocation(location: Location)

    suspend fun insertLocation(location: Location)

    suspend fun deleteLocation(location: Location)

    suspend fun getLocations(): List<Location>
}
package com.example.tracker.models.locations

interface LocationsRepository {

    suspend fun saveLocation(location: Location)

    suspend fun uploadLocation()

}

package com.example.models.locations.network

import com.example.models.locations.Location

interface LocationsNetwork {

    suspend fun uploadLocation(location: Location)

    suspend fun downloadLocations(ownerId: String, startDate: Long, endDate: Long): List<Location>

}

package com.example.tracker.models.locations.network

import com.example.tracker.models.locations.Location

interface LocationsNetwork {

    suspend fun uploadLocation(location: Location)

}

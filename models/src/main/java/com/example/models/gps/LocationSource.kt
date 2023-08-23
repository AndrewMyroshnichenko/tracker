package com.example.models.gps

import com.example.models.locations.Location
import kotlinx.coroutines.flow.Flow

interface LocationSource {

    fun observeLocations(): Flow<Location>

    fun getGpsStatusFlow(): Flow<Boolean>

    class LocationException(message: String) : Exception(message)

}

package com.example.tracker.models.gps

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationSource {

    fun observeLocations(): Flow<Location>

    fun getGpsStatusFlow(): Flow<Boolean>

    class LocationException(message: String) : Exception(message)

}

package com.example.tracker.models.gps

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationSource {

    fun getLocationUpdates(): Flow<Location>

    class LocationException(message: String) : Exception(message)

}

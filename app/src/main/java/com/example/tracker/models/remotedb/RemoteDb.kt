package com.example.tracker.models.remotedb

import com.example.tracker.data.locations.Location

interface RemoteDb {

    suspend fun addLocation(location: Location)

}
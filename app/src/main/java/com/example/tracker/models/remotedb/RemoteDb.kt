package com.example.tracker.models.remotedb

import com.example.tracker.data.locations.Location

interface RemoteDb {

    fun addLocation(location: Location)

}
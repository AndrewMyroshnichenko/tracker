package com.example.tracker.models.gps

interface LocationServiceInterface {

    fun getLocationUpdates(location: LocationSource)

    fun stop()

    fun destroy()
}
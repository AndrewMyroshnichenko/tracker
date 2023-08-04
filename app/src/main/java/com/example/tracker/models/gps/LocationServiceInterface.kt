package com.example.tracker.models.gps

interface LocationServiceInterface {

    fun getLocationUpdates()

    fun stop()

    fun destroy()
}
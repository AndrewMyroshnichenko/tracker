package com.example.tracker.models.gps

interface LocationServiceInterface {

    fun getLocationUpdates()

    fun onCreate()

    fun stop()

    fun destroy()
}
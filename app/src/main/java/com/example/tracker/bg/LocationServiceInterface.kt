package com.example.tracker.bg

interface LocationServiceInterface {

    fun startLocationUpdates()

    fun stopLocationUpdates()

    fun destroy()
}

package com.example.tracker.bg

interface LocationController {

    fun startLocationUpdates()

    fun stopLocationUpdates()

    fun destroy()
}

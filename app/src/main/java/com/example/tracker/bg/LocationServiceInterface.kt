package com.example.tracker.bg

interface LocationServiceInterface {

    fun getLocationUpdates()

    fun stop()

    fun destroy()
}

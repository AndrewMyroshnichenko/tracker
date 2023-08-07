package com.example.tracker.models.gps

import kotlinx.coroutines.flow.Flow

interface LocationInterface {
    fun getGpsStatus(): Flow<Boolean>


    fun setGpsStatus(isEnabled: Boolean)

    fun getServiceStatus(): Flow<Boolean>


    fun setServiceStatus(isEnabled: Boolean)
}
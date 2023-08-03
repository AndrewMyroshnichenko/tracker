package com.example.tracker.models.gps

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object LocationModel {

    private val isGpsEnabled = MutableStateFlow(true)
    private val isServiceOn = MutableStateFlow(false)

    fun getGpsStatus(): Flow<Boolean> = isGpsEnabled


    fun setGpsStatus(isEnabled: Boolean) {
        isGpsEnabled.value = isEnabled
    }

    fun getServiceStatus(): Flow<Boolean> = isServiceOn


    fun setServiceStatus(isEnabled: Boolean) {
        isServiceOn.value = isEnabled
    }
}

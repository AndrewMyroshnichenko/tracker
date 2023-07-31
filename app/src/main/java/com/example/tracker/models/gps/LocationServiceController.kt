package com.example.tracker.models.gps

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object LocationServiceController {

    private val isGpsEnabled = MutableStateFlow(true)

    fun getGpsStatus(): StateFlow<Boolean> {
        return isGpsEnabled
    }

    fun setGpsStatus(isEnabled: Boolean) {
        isGpsEnabled.value = isEnabled
    }

}
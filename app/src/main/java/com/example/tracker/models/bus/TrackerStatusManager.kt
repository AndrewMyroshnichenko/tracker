package com.example.tracker.models.bus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TrackerStatusManager : StatusManager {

    private val isServiceOn = MutableStateFlow(false)
    private val isGpsEnabled = MutableStateFlow(false)

    override fun getGpsStatus(): Flow<Boolean> = isGpsEnabled

    override fun setGpsStatus(isEnabled: Boolean) {
        isGpsEnabled.value = isEnabled
    }

    override fun getServiceStatus(): Flow<Boolean> = isServiceOn


    override fun setServiceStatus(isEnabled: Boolean) {
        isServiceOn.value = isEnabled
    }
}

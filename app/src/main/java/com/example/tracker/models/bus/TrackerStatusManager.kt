package com.example.tracker.models.bus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TrackerStatusManager (
    private val isServiceOn: MutableStateFlow<Boolean>,
    private val isGpsEnabled: MutableStateFlow<Boolean>
) : StatusManager {

    override fun getGpsStatus(): Flow<Boolean> = isGpsEnabled

    override fun setGpsStatus(isEnabled: Boolean) {
        isGpsEnabled.value = isEnabled
    }

    override fun getServiceStatus(): Flow<Boolean> = isServiceOn


    override fun setServiceStatus(isEnabled: Boolean) {
        isServiceOn.value = isEnabled
    }
}

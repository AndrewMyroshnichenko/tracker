package com.example.tracker.models.gps

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

class LocationModel (
    private val isServiceOn: MutableStateFlow<Boolean>,
    private val isGpsEnabled: MutableStateFlow<Boolean>
) : LocationInterface {

    override fun getGpsStatus(): Flow<Boolean> = isGpsEnabled


    override fun setGpsStatus(isEnabled: Boolean) {
        isGpsEnabled.value = isEnabled
    }

    override fun getServiceStatus(): Flow<Boolean> = isServiceOn


    override fun setServiceStatus(isEnabled: Boolean) {
        isServiceOn.value = isEnabled
    }
}

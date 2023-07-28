package com.example.tracker.models.gps


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object LocationServiceController {

    private val isGpsEnabled = MutableLiveData<Boolean>()

    fun getGpsStatus(): LiveData<Boolean> {
        return isGpsEnabled
    }

    fun setGpsStatus(isEnabled: Boolean) {
        isGpsEnabled.value = isEnabled
    }

}
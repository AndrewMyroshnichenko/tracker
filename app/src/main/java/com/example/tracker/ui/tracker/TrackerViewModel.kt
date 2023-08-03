package com.example.tracker.ui.tracker

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.gps.LocationModel
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.tracker.state.TrackerEffect
import com.example.tracker.ui.tracker.state.TrackerState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TrackerViewModel(
    private val firebaseManager: Auth
) : MviViewModel<TrackerContract.View, TrackerState>(), TrackerContract.ViewModel {

    private var serviceIsRunning = false
    private var gpsIsOn = false

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            if (getState() == null) {
                setState(TrackerState(false, gpsIsOn))
            }
            viewModelScope.launch {
                combine(LocationModel.getServiceStatus(), LocationModel.getGpsStatus()) { servStatus, gpsStatus ->
                    serviceIsRunning = servStatus
                    gpsIsOn = gpsStatus
                    TrackerState(serviceIsRunning, gpsIsOn)
                }.collect { newState ->
                    setState(newState)
                }
            }
        }
    }

    override fun buttonToggle() {
        if (serviceIsRunning) {
            stopTrack()
        } else {
            startTrack()
        }

    }

    private fun startTrack() {
        setState(TrackerState(true, gpsIsOn))
    }

    private fun stopTrack() {
        setState(TrackerState(false, gpsIsOn))
    }

    override fun singOut() {
        firebaseManager.signOut()
        setEffect(TrackerEffect.NavigateAfterLogOut())
    }
}

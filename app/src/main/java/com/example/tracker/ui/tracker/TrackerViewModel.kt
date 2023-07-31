package com.example.tracker.ui.tracker

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.gps.LocationServiceController
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.tracker.state.TrackerEffect
import com.example.tracker.ui.tracker.state.TrackerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrackerViewModel(
    private val firebaseManager: Auth
) : MviViewModel<TrackerContract.View, TrackerState>(), TrackerContract.ViewModel {


    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            if (getState() == null) {
                setState(TrackerState(false, isGpsAvailable().value))
            }

            viewModelScope.launch {
                isGpsAvailable().collect {
                    getState()?.serviceRunning?.let { serviceRunning ->
                        setState(TrackerState(serviceRunning, it))
                    }
                }
            }
        }
    }

    override fun buttonToggle() {
        if (getState()?.serviceRunning == true) {
            stopTrack()
        } else {
            startTrack()
        }

    }

    private fun startTrack() {
        setState(TrackerState(true, isGpsAvailable().value))
    }

    private fun stopTrack() {
        setState(TrackerState(false, isGpsAvailable().value))
    }

    override fun singOut() {
        firebaseManager.signOut()
        setEffect(TrackerEffect.NavigateAfterLogOut())
    }

    override fun isGpsAvailable(): StateFlow<Boolean> {
        return LocationServiceController.getGpsStatus()
    }
}
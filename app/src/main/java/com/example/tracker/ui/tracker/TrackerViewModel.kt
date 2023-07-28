package com.example.tracker.ui.tracker

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.gps.LocationServiceController
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.tracker.state.TrackerEffect
import com.example.tracker.ui.tracker.state.TrackerState

class TrackerViewModel(
    private val firebaseManager: Auth
) : MviViewModel<TrackerContract.View, TrackerState>(), TrackerContract.ViewModel {

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            if (getState() == null) {
                setState(TrackerState())
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
        setState(TrackerState(true))
    }

    private fun stopTrack() {
        setState(TrackerState(false))
    }

    override fun singOut() {
        firebaseManager.signOut()
        setEffect(TrackerEffect.NavigateAfterLogOut())
    }

    override fun isGpsAvailable(): LiveData<Boolean> {
        return LocationServiceController.getGpsStatus()
    }


}

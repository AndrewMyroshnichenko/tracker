package com.example.tracker.ui.tracker

import com.example.tracker.models.auth.Auth
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.tracker.state.TrackerEffect
import com.example.tracker.ui.tracker.state.TrackerState

class TrackerViewModel(
    private val firebaseManager: Auth
) : MviViewModel<TrackerContract.View, TrackerState>(), TrackerContract.ViewModel {

    override fun onCreate() {
        if (getState() == null) {
            setState(TrackerState())
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

}

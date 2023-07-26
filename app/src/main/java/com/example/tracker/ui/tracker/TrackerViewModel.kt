package com.example.tracker.ui.tracker

import com.example.tracker.models.auth.Auth
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.tracker.state.TrackerEffect
import com.example.tracker.ui.tracker.state.TrackerState

class TrackerViewModel(
    private val firebaseManager: Auth
) : MviViewModel<TrackerContract.View, TrackerState>(), TrackerContract.ViewModel {

    override fun singOut() {
        firebaseManager.signOut()
        setEffect(TrackerEffect.NavigateAfterLogOut())
    }

    override fun startTrack(isProviderEnabled: Boolean) {
        if (isProviderEnabled) {
            setState(TrackerState(true, isProviderEnabled))
        } else {
            setState(TrackerState(false, isProviderEnabled))
        }
    }

    override fun stopTrack(isProviderEnabled: Boolean) {
        setState(TrackerState(false, isProviderEnabled))
    }
}

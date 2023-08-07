package com.example.tracker.ui.tracker

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.gps.LocationInterface
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.tracker.state.TrackerEffect
import com.example.tracker.ui.tracker.state.TrackerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    val firebaseManager: Auth, val locationModel: LocationInterface
) : MviViewModel<TrackerContract.View, TrackerState>(), TrackerContract.ViewModel {

    private var isServiceRunning = false
    private var isGpsOn = true

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            if (getState() == null) {
                setState(TrackerState(false, isGpsOn))
            }
            viewModelScope.launch {
                combine(
                    locationModel.getServiceStatus(),
                    locationModel.getGpsStatus()
                ) { servStatus, gpsStatus ->
                    isServiceRunning = servStatus
                    isGpsOn = gpsStatus
                    Log.d("TAG","VM s $isServiceRunning g $isGpsOn")
                    TrackerState(isServiceRunning, isGpsOn)
                }.collect { newState ->
                    setState(newState)
                }
            }
        }
    }

    override fun buttonToggle() {
        if (isServiceRunning) {
            stopTrack()
        } else {
            startTrack()
        }

    }

    private fun startTrack() {
        setState(TrackerState(true, isGpsOn))
    }

    private fun stopTrack() {
        setState(TrackerState(false, isGpsOn))
    }

    override fun singOut() {
        firebaseManager.signOut()
        setEffect(TrackerEffect.NavigateAfterLogOut())
    }
}

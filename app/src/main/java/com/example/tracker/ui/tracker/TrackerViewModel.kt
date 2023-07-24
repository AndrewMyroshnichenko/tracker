package com.example.tracker.ui.tracker

import android.location.LocationManager
import com.example.tracker.domain.LocationService
import com.example.tracker.models.Authentication
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.tracker.state.TrackerEffect
import com.example.tracker.ui.tracker.state.TrackerState

class TrackerViewModel(private val firebaseManager: Authentication
) : MviViewModel<TrackerContract.View, TrackerState>(), TrackerContract.ViewModel {


    override fun singOut() {
        firebaseManager.signOut()
        setEffect(TrackerEffect.NavigateAfterLogOut())
    }

    override fun startTrack(locationManager : LocationManager) {

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            setState(TrackerState.TrackerCollectsLocationState(LocationService.ACTION_START))
        }else{
            setState(TrackerState.GpsIsOffState)
        }
    }

    override fun stopTrack() {
        setState(TrackerState.TrackerIsOffState(LocationService.ACTION_STOP))
    }


}
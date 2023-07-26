package com.example.tracker.ui.tracker.state

import com.example.tracker.mvi.states.AbstractState
import com.example.tracker.ui.tracker.TrackerContract

open class TrackerState(private val serviceRunning: Boolean, private val gpsEnabled: Boolean) :
    AbstractState<TrackerContract.View, TrackerState>() {

    override fun visit(screen: TrackerContract.View) {
        screen.showTrackerState(serviceRunning, gpsEnabled)
    }


    data class TrackerIsOffState(
        val isServiceWorking: Boolean, val isGpsAvailable: Boolean
    ) : TrackerState(isServiceWorking, isGpsAvailable)

    data class TrackerCollectsLocationState(
        val isServiceWorking: Boolean, val isGpsAvailable: Boolean
    ) : TrackerState(isServiceWorking, isGpsAvailable)

    data class GpsIsOffState(
        val isServiceWorking: Boolean, val isGpsAvailable: Boolean
    ) : TrackerState(isServiceWorking, isGpsAvailable)
}

package com.example.tracker.ui.tracker.state

import com.example.tracker.mvi.states.AbstractState
import com.example.tracker.ui.tracker.TrackerContract

open class TrackerState : AbstractState<TrackerContract.View, TrackerState>() {


    data class TrackerIsOffState(val action: String) : TrackerState() {

        override fun visit(screen: TrackerContract.View) {
            super.visit(screen)
            screen.showTrackerIsOff()
            screen.startStopService(action)
        }

    }


    data class TrackerCollectsLocationState(val action: String) : TrackerState() {

        override fun visit(screen: TrackerContract.View) {
            super.visit(screen)
            screen.showTrackerCollectsLocation()
            screen.startStopService(action)
        }

    }

    object GpsIsOffState : TrackerState() {

        override fun visit(screen: TrackerContract.View) {
            super.visit(screen)
            screen.showGpsIsOff()
        }

    }
}
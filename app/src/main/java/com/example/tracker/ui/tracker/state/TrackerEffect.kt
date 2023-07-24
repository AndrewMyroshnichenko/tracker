package com.example.tracker.ui.tracker.state

import com.example.tracker.mvi.states.AbstractEffect
import com.example.tracker.ui.tracker.TrackerContract

open class TrackerEffect : AbstractEffect<TrackerContract.View>() {
    class NavigateAfterLogOut : TrackerEffect() {
        override fun visit(screen: TrackerContract.View) {
            super.visit(screen)
            screen.nextScreen()
        }
    }
}
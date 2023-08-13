package com.example.tracker.ui.map.state

import com.example.tracker.mvi.states.AbstractEffect
import com.example.tracker.ui.map.MapContract

open class MapEffect : AbstractEffect<MapContract.View>() {
    class NavigateAfterLogOut : MapEffect() {
        override fun handle(screen: MapContract.View) {
            screen.proceedToLoginScreen()
        }
    }
}
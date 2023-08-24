package com.example.map.ui.state

import com.example.mvi.states.AbstractEffect
import com.example.map.ui.MapContract

open class MapEffect : AbstractEffect<MapContract.View>() {

    class NavigateAfterLogOut : MapEffect() {
        override fun handle(screen: MapContract.View) {
            screen.proceedToLoginScreen()
        }
    }

    class ShowMessage(private val messageId: Int) : MapEffect() {
        override fun handle(screen: MapContract.View) {
            screen.showMessage(messageId)
        }
    }

}

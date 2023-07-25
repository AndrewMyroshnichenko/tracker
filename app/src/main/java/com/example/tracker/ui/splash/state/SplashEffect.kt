package com.example.tracker.ui.splash.state

import com.example.tracker.mvi.states.AbstractEffect
import com.example.tracker.ui.splash.SplashContract

open class SplashEffect : AbstractEffect<SplashContract.View>() {

    object ProceedToLoginScreen : SplashEffect() {
        override fun handle(screen: SplashContract.View) {
            screen.proceedToLoginScreen()
        }
    }

    object ProceedToTrackerScreen : SplashEffect() {
        override fun handle(screen: SplashContract.View) {
            screen.proceedToTrackerScreen()
        }
    }
}

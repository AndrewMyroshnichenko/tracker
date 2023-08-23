package com.example.tracker.ui.splash.state

import com.example.mvi.states.AbstractEffect
import com.example.tracker.ui.splash.SplashContract

open class SplashEffect : AbstractEffect<SplashContract.View>() {

    class ProceedToLoginScreen : SplashEffect() {
        override fun handle(screen: SplashContract.View) {
            screen.proceedToAuthScreen()
        }
    }

    class ProceedToTrackerScreen : SplashEffect() {
        override fun handle(screen: SplashContract.View) {
            screen.proceedToMainScreen()
        }
    }
}

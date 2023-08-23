package com.example.auth.splash.state

import com.example.mvi.states.AbstractEffect
import com.example.auth.splash.SplashContract

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

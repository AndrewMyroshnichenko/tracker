package com.example.tracker.ui.splash

import com.example.tracker.models.FirebaseInterface
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.splash.state.SplashEffect
import com.example.tracker.ui.splash.state.SplashState

class SplashViewModel(private val firebaseManager: FirebaseInterface) :
    MviViewModel<SplashContract.View, SplashState>(), SplashContract.ViewModel {

    @Suppress("UNREACHABLE_CODE")
    override fun isSignedIn(): Boolean {
        setEffect(SplashEffect.NextScreen)
        return firebaseManager.isSignedIn()

    }

}
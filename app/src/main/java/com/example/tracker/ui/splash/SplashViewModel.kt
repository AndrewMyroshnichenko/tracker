package com.example.tracker.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.models.Authentication
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.splash.state.SplashEffect
import com.example.tracker.ui.splash.state.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val firebaseManager: Authentication) :
    MviViewModel<SplashContract.View, SplashState>(), SplashContract.ViewModel {

    private val splashDelay = 1000L

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            viewModelScope.launch {
                delay(splashDelay)
                if (firebaseManager.isSignedIn()) {
                    setEffect(SplashEffect.ProceedToTrackerScreen)
                } else {
                    setEffect(SplashEffect.ProceedToLoginScreen)
                }
            }
        }
    }

}
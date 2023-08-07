package com.example.tracker.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.models.auth.Auth
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.splash.state.SplashEffect
import com.example.tracker.ui.splash.state.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val auth: Auth) :
    MviViewModel<SplashContract.View, SplashState>(), SplashContract.ViewModel {

    private val splashDelay = 1000L

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            viewModelScope.launch {
                delay(splashDelay)
                if (auth.isSignedIn()) {
                    setEffect(SplashEffect.ProceedToTrackerScreen)
                } else {
                    setEffect(SplashEffect.ProceedToLoginScreen)
                }
            }
        }
    }
}

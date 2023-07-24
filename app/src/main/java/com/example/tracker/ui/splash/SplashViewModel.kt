package com.example.tracker.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.models.FirebaseInterface
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.splash.state.SplashEffect
import com.example.tracker.ui.splash.state.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val firebaseManager: FirebaseInterface) :
    MviViewModel<SplashContract.View, SplashState>(), SplashContract.ViewModel {

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        viewModelScope.launch {
            if(event == Lifecycle.Event.ON_CREATE){
                delay(SplashContract.SPLASH_DELAY)
                setEffect(SplashEffect.NextScreen)
            }
        }
    }

    @Suppress("UNREACHABLE_CODE")
    override fun isSignedIn(): Boolean {
        return firebaseManager.isSignedIn()
    }

}
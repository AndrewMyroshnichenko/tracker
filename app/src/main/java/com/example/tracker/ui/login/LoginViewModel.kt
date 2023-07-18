package com.example.tracker.ui.login

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.firebase.FirebaseInterface
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.login.state.LoginLoadingEffect
import com.example.tracker.ui.login.state.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val firebaseManager: FirebaseInterface) :
    MviViewModel<LoginContract.View, LoginState>() {

    fun signIn(email: String, password: String) {
        setEffect(LoginLoadingEffect())
        firebaseManager.signIn(email, password) { success, message ->
            if (success) {
                setState(LoginState.LoginSuccessState(message))
            } else {
                setState(LoginState.LoginErrorState(message))
            }
        }
    }

    fun signUp(email: String, password: String) {
        setEffect(LoginLoadingEffect())
        firebaseManager.signUp(email, password) { success, message ->
            if (success) {
                setState(LoginState.LoginSuccessState(message))
            } else {
                setState(LoginState.LoginErrorState(message))
            }
        }
    }

    fun forgotPassword(email: String) {
        setEffect(LoginLoadingEffect())
        firebaseManager.forgotPassword(email) { success, message ->
            if (success) {
                setState(LoginState.LoginForgotPasswordState)
            } else {
                setState(LoginState.LoginErrorState(message))
            }
        }
    }

/*    fun checkUserSignedIn() {
        if (firebaseManager.isSignedIn()) {
            setState(LoginState.LoginSuccessState(null))
        }
    }

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            checkUserSignedIn()
        }
    }*/


}
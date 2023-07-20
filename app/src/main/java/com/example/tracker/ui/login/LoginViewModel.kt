package com.example.tracker.ui.login

import android.util.Patterns
import com.example.tracker.firebase.FirebaseInterface
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.login.state.LoginEffect
import com.example.tracker.ui.login.state.LoginState

class LoginViewModel(val firebaseManager: FirebaseInterface) :
    MviViewModel<LoginContract.View, LoginState>(), LoginContract.ViewModel {

    override fun signIn(userEmail: String, userPass: String) {
        setEffect(LoginEffect.Loading)
        firebaseManager.signIn(userEmail, userPass) { success, message ->
            if (success) {
                setState(LoginState.LoginSuccessState(message))
            } else {
                setState(LoginState.LoginErrorState(message))
            }
        }
    }

    override fun signUp(userEmail: String, userPassFirst: String, userPassSecond: String) {
        setEffect(LoginEffect.Loading)

        if (!isEmailValid(userEmail)){
            setEffect(LoginEffect.Error("Write correct email"))
        } else if (!isPasswordValid(userPassFirst)) {
            setEffect(LoginEffect.Error("To short password!"))
        } else if (!arePasswordsMatch(userPassFirst, userPassSecond)) {
            setEffect(LoginEffect.Error("Passwords mismatch!"))
        } else {
            firebaseManager.signUp(userEmail, userPassFirst) { success, message ->
                if (success) {
                    setState(LoginState.LoginSuccessState(message))
                } else {
                    setState(LoginState.LoginErrorState(message))
                }
            }
        }
    }

    override fun forgotPassword(userEmail: String) {
        setEffect(LoginEffect.Loading)
        if (isEmailValid(userEmail)){
            firebaseManager.forgotPassword(userEmail) { success, message ->
                if (success) {
                    setState(LoginState.LoginForgotPasswordState)
                } else {
                    setState(LoginState.LoginErrorState(message))
                }
            }
        } else {
            setEffect(LoginEffect.Error("Invalid email!"))
        }
    }

    private fun isEmailValid(userEmail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
    }

    private fun isPasswordValid(userPass: String): Boolean {
        return userPass.length > 7
    }

    private fun arePasswordsMatch(firstPass: String, secondPass: String): Boolean {
        return firstPass == secondPass
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
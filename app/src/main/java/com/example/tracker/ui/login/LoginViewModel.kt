package com.example.tracker.ui.login

import android.util.Patterns
import com.example.tracker.firebase.FirebaseInterface
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.login.state.LoginEffect
import com.example.tracker.ui.login.state.LoginState

class LoginViewModel(val firebaseManager: FirebaseInterface) :
    MviViewModel<LoginContract.View, LoginState>(), LoginContract.ViewModel {

    override fun signIn(userEmail: String, userPass: String) {
        if (isEmailValid(userEmail)) {
            firebaseManager.signIn(userEmail, userPass) { success, message ->
                if (success) {
                    setEffect(LoginEffect.NavigateAfterSignIn)
                } else {
                    setState(LoginState.LoginErrorState(message))
                }
            }
        } else {
            setState(LoginState.LoginErrorState("Write correct email!"))
        }
    }

    override fun signUp(userEmail: String, userPassFirst: String, userPassSecond: String) {

        if (!isEmailValid(userEmail)){
            setState(LoginState.LoginErrorState("Write correct email!"))
        } else if (!isPasswordValid(userPassFirst)) {
            setState(LoginState.LoginErrorState("To short password!"))
        } else if (!arePasswordsMatch(userPassFirst, userPassSecond)) {
            setState(LoginState.LoginErrorState("Passwords mismatch!"))
        } else {
            firebaseManager.signUp(userEmail, userPassFirst) { success, message ->
                if (success) {
                    setEffect(LoginEffect.ShowSuccessMessage(message))
                } else {
                    setState(LoginState.LoginErrorState(message))
                }
            }
        }
    }

    override fun forgotPassword(userEmail: String) {
        if (isEmailValid(userEmail)){
            firebaseManager.forgotPassword(userEmail) { success, message ->
                if (success) {
                    setEffect(LoginEffect.ShowSuccessMessage(message))
                } else {
                    setState(LoginState.LoginErrorState(message))
                }
            }
        } else {
            setState(LoginState.LoginErrorState("Write correct email!"))
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
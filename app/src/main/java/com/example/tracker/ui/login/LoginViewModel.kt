package com.example.tracker.ui.login

import android.util.Patterns
import com.example.tracker.R
import com.example.tracker.models.auth.Auth
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.login.state.LoginEffect
import com.example.tracker.ui.login.state.LoginState

class LoginViewModel(
    private val firebaseManager: Auth
) : MviViewModel<LoginContract.View, LoginState>(), LoginContract.ViewModel {

    override fun signIn(userEmail: String, userPass: String) {
        if (userEmail.isEmpty() || userPass.isEmpty()) {
            setState(LoginState.LoginErrorState(R.string.email_or_password_is_empty))
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState.LoginErrorState(R.string.write_correct_email))
        } else {
            firebaseManager.signIn(userEmail, userPass) { success, message ->
                if (success) {
                    setEffect(LoginEffect.NavigateAfterSignIn)
                    setState(LoginState.LoginErrorState(-1))
                } else {
                    setState(LoginState.LoginErrorState(message))
                }
            }
        }
    }

    override fun signUp(userEmail: String, userPassFirst: String, userPassSecond: String) {
        if (userEmail.isEmpty() || userPassFirst.isEmpty() || userPassSecond.isEmpty()) {
            setState(LoginState.LoginErrorState(R.string.email_or_password_is_empty))
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState.LoginErrorState(R.string.write_correct_email))
        } else if (!isPasswordValid(userPassFirst)) {
            setState(LoginState.LoginErrorState(R.string.to_short_password))
        } else if (!arePasswordsMatch(userPassFirst, userPassSecond)) {
            setState(LoginState.LoginErrorState(R.string.passwords_mismatch))
        } else {
            firebaseManager.signUp(userEmail, userPassFirst) { success, message ->
                if (success) {
                    setEffect(LoginEffect.ShowSuccessMessage(message))
                    setState(LoginState.LoginErrorState(-1))
                } else {
                    setState(LoginState.LoginErrorState(message))
                }
            }
        }
    }

    override fun forgotPassword(userEmail: String) {
        if (userEmail.isEmpty()) {
            setState(LoginState.LoginErrorState(R.string.email_is_empty))
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState.LoginErrorState(R.string.write_correct_email))
        } else {
            firebaseManager.forgotPassword(userEmail) { success, message ->
                if (success) {
                    setEffect(LoginEffect.ShowSuccessMessage(message))
                    setState(LoginState.LoginErrorState(-1))
                } else {
                    setState(LoginState.LoginErrorState(message))
                }
            }
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
}

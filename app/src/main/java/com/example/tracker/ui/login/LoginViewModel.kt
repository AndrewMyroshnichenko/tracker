package com.example.tracker.ui.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.tracker.R
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.auth.FirebaseAuth
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.login.state.LoginEffect
import com.example.tracker.ui.login.state.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuth: Auth
) : MviViewModel<LoginContract.View, LoginState>(), LoginContract.ViewModel {

    override fun signIn(userEmail: String, userPass: String) {
        if (userEmail.isEmpty()) {
            setState(LoginState(loginError = R.string.email_is_empty))
            if (isPassLengthEnough(userPass)) {
                setState(LoginState(R.string.email_is_empty, R.string.to_short_password))
            }
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState(loginError = R.string.write_correct_email))
            if (isPassLengthEnough(userPass)) {
                setState(LoginState(R.string.write_correct_email, R.string.to_short_password))
            }
        } else if (isPassLengthEnough(userPass)) {
            setState(LoginState(passError = R.string.to_short_password))
        } else {
            viewModelScope.launch {
                try {
                    firebaseAuth.signIn(userEmail, userPass)
                    setEffect(LoginEffect.NavigateAfterSignIn)
                    setState(LoginState())
                } catch (e: Exception) {
                    setState(LoginState(loginError = R.string.login_failed))
                }
            }
        }
    }

    override fun signUp(userEmail: String, userPassFirst: String, userPassSecond: String) {
        if (userEmail.isEmpty() && userPassFirst.isEmpty() && userPassSecond.isEmpty()) {
            setState(LoginState(loginError = R.string.email_is_empty))
            if (isPassLengthEnough(userPassFirst)) {
                setState(LoginState(R.string.email_is_empty, R.string.to_short_password))
            }
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState(loginError = R.string.write_correct_email))
            if (isPassLengthEnough(userPassFirst)) {
                setState(LoginState(R.string.write_correct_email, R.string.to_short_password))
            }
        } else if (!isPassLengthEnough(userPassFirst)) {
            setState(LoginState(passError = R.string.to_short_password))
        } else if (!arePasswordsMatch(userPassFirst, userPassSecond)) {
            setState(LoginState(passError = R.string.passwords_mismatch))
        } else {
            viewModelScope.launch {
                try {
                    firebaseAuth.signUp(userEmail, userPassFirst)
                    setEffect(LoginEffect.ShowSuccessMessage(R.string.registration_completed))
                    setState(LoginState())
                } catch (e: Exception) {
                    setState(LoginState(loginError = R.string.registration_failed))
                }
            }
        }
    }

    override fun forgotPassword(userEmail: String) {
        if (userEmail.isEmpty()) {
            setState(LoginState(loginError = R.string.email_is_empty))
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState(loginError = R.string.write_correct_email))
        } else {
            viewModelScope.launch {
                try {
                    firebaseAuth.forgotPassword(userEmail)
                    setEffect(LoginEffect.ShowSuccessMessage(R.string.check_your_email))
                    setState(LoginState())
                } catch (e: Exception) {
                    setState(LoginState(loginError = R.string.something_went_wrong_password_wasnt_reset))
                }
            }
        }
    }

    private fun isEmailValid(userEmail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
    }

    private fun isPassLengthEnough(userPass: String): Boolean {
        return userPass.length <= FirebaseAuth.MIN_PASS_LENGTH
    }

    private fun arePasswordsMatch(firstPass: String, secondPass: String): Boolean {
        return firstPass == secondPass
    }
}

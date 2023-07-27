package com.example.tracker.ui.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.tracker.R
import com.example.tracker.models.auth.Auth
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.login.state.LoginEffect
import com.example.tracker.ui.login.state.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuth: Auth
) : MviViewModel<LoginContract.View, LoginState>(), LoginContract.ViewModel {

    override fun signIn(userEmail: String, userPass: String) {
        if (userEmail.isEmpty()) {
            setState(LoginState.UserNameErrorState(R.string.email_is_empty))
        } else if (userPass.isEmpty()) {
            setState(LoginState.PasswordErrorState(R.string.password_is_empty))
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState.UserNameErrorState(R.string.write_correct_email))
        } else {
            viewModelScope.launch {
                try {
                    firebaseAuth.signIn(userEmail, userPass)
                    setEffect(LoginEffect.NavigateAfterSignIn)
                    setState(LoginState.UserNameErrorState(R.string.empty_error_message))
                } catch (e: Exception) {
                    setState(LoginState.UserNameErrorState(R.string.login_failed))
                }
            }
        }
    }

    override fun signUp(userEmail: String, userPassFirst: String, userPassSecond: String) {
        if (userEmail.isEmpty() && userPassFirst.isEmpty() && userPassSecond.isEmpty()){

        } else if (userEmail.isEmpty()) {
            setState(LoginState.UserNameErrorState(R.string.email_is_empty))
        } else if (userPassFirst.isEmpty() || userPassSecond.isEmpty()) {
            setState(LoginState.PasswordErrorState(R.string.password_is_empty))
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState.UserNameErrorState(R.string.write_correct_email))
        } else if (!isPasswordValid(userPassFirst)) {
            setState(LoginState.PasswordErrorState(R.string.to_short_password))
        } else if (!arePasswordsMatch(userPassFirst, userPassSecond)) {
            setState(LoginState.PasswordErrorState(R.string.passwords_mismatch))
        } else {
            viewModelScope.launch {
                try {
                    firebaseAuth.signUp(userEmail, userPassFirst)
                    setEffect(LoginEffect.ShowSuccessMessage(R.string.registration_completed))
                    setState(LoginState.UserNameErrorState(R.string.empty_error_message))
                }catch (e: Exception){
                    setState(LoginState.UserNameErrorState(R.string.registration_failed))
                }
            }
        }
    }

    override fun forgotPassword(userEmail: String) {
        if (userEmail.isEmpty()) {
            setState(LoginState.UserNameErrorState(R.string.email_is_empty))
        } else if (!isEmailValid(userEmail)) {
            setState(LoginState.UserNameErrorState(R.string.write_correct_email))
        } else {
            viewModelScope.launch {
                try {
                    firebaseAuth.forgotPassword(userEmail)
                    setEffect(LoginEffect.ShowSuccessMessage(R.string.check_your_email))
                    setState(LoginState.UserNameErrorState(R.string.empty_error_message))
                } catch (e: Exception) {
                    setState(LoginState.UserNameErrorState(R.string.something_went_wrong_password_wasnt_reset))
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

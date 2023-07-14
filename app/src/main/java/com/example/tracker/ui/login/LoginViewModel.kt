package com.example.tracker.ui.login

import androidx.lifecycle.viewModelScope
import com.example.tracker.FirebaseInterface
import com.example.tracker.mvi.MviViewModel
import kotlinx.coroutines.launch

class LoginViewModel(val firebaseManager: FirebaseInterface) :
    MviViewModel<LoginState, LoginEvent, LoginAction>() {

    fun dispatchEvent(event: LoginEvent) {
        handleAction(eventToAction(event))
    }

    override fun eventToAction(event: LoginEvent): LoginAction {
        return when (event) {
            is LoginEvent.SingInEvent -> LoginAction.DoSingInEvent(event.email, event.password)
            is LoginEvent.SingUpEvent -> LoginAction.DoSingUpEvent(event.email, event.password)
            is LoginEvent.SingOutEvent -> LoginAction.DoSingOutEvent
            is LoginEvent.ForgotPasswordEvent -> LoginAction.DoForgotPasswordEvent(event.email)
        }
    }

    override fun handleAction(action: LoginAction) {
        viewModelScope.launch {
            when (action) {
                is LoginAction.DoSingInEvent -> signIn(action.email, action.password)
                is LoginAction.DoSingUpEvent -> signUp(action.email, action.password)
                is LoginAction.DoSingOutEvent -> signOut()
                is LoginAction.DoForgotPasswordEvent -> forgotPassword(action.email)
            }

        }
    }

    private fun signIn(email: String, password: String) {
        firebaseManager.signIn(email, password) { success, errorMessage ->
            if (success) {
                mState.postValue(LoginState.SuccessSignIn(email))
            } else {
                mState.postValue(LoginState.ShowError(errorMessage))
            }
        }
    }

    private fun signUp(email: String, password: String) {
        firebaseManager.signUp(email, password) { success, errorMessage ->
            if (success) {
                mState.postValue(LoginState.SuccessSignUp)
            } else {
                mState.postValue(LoginState.ShowError(errorMessage))
            }
        }
    }

    private fun forgotPassword(email: String) {
        firebaseManager.forgotPassword(email) { success, errorMessage ->
            if (success) {
                mState.postValue(LoginState.SuccessSignUp)
            } else {
                mState.postValue(LoginState.ShowError(errorMessage))
            }
        }
    }

    private fun signOut() {
        firebaseManager.signOut()
        mState.postValue(LoginState.SuccessSignOutState)
    }


}
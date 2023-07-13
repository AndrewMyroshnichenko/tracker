package com.example.tracker.ui.login

import androidx.lifecycle.viewModelScope
import com.example.tracker.FirebaseInterface
import com.example.tracker.mvi.MviViewModel
import kotlinx.coroutines.launch

class LoginViewModel(val mAuth: FirebaseInterface) :
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
                is LoginAction.DoSingInEvent -> mState.postValue(mAuth.signIn(action.email, action.password))
                is LoginAction.DoSingUpEvent -> mState.postValue(mAuth.signUp(action.email, action.password))
                is LoginAction.DoSingOutEvent -> mState.postValue(mAuth.signOut())
                is LoginAction.DoForgotPasswordEvent -> mState.postValue(mAuth.forgotPassword(action.email))
            }

        }
    }


}
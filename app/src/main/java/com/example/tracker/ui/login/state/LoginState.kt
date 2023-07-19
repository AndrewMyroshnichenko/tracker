package com.example.tracker.ui.login.state

import com.example.tracker.mvi.states.AbstractState
import com.example.tracker.ui.login.LoginContract

open class LoginState : AbstractState<LoginContract.View, LoginState>(){

    data class LoginSuccessState(val email: String?) : LoginState() {
        override fun merge(prevState: LoginState): LoginState {
            if (prevState is LoginLoadingState) {
                return LoginSuccessState(email)
            }
            return this
        }
    }

    data class LoginLoadingState(val isLoading: Boolean) : LoginState() {
        override fun merge(prevState: LoginState): LoginState {
            if (prevState is LoginSuccessState) {
                return LoginLoadingState(isLoading)
            }
            return this
        }
    }

    data class LoginErrorState(val errorMessage: String?) : LoginState() {
        override fun merge(prevState: LoginState): LoginState {
            if (prevState is LoginLoadingState) {
                return LoginErrorState(errorMessage)
            }
            return this
        }
    }

    object LoginForgotPasswordState : LoginState()

}
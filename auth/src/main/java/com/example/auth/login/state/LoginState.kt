package com.example.auth.login.state

import com.example.auth.R
import com.example.mvi.states.AbstractState
import com.example.auth.login.LoginContract

open class LoginState(
    private val loginError: Int = R.string.empty_error_message,
    private val passError: Int = R.string.empty_error_message
) : AbstractState<LoginContract.View, LoginState>() {

    override fun visit(screen: LoginContract.View) {
        screen.showLoginError(loginError)
        screen.showPasswordError(passError)
    }
}

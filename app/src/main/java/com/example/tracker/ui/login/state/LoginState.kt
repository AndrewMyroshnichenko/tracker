package com.example.tracker.ui.login.state

import com.example.tracker.R
import com.example.tracker.mvi.states.AbstractState
import com.example.tracker.ui.login.LoginContract

open class LoginState(
    private val loginError: Int = R.string.empty_error_message,
    private val passError: Int = R.string.empty_error_message
) : AbstractState<LoginContract.View, LoginState>() {

    override fun visit(screen: LoginContract.View) {
        screen.showLoginError(loginError)
        screen.showPasswordError(passError)
    }
}

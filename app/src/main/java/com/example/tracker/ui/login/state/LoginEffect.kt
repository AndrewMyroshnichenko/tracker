package com.example.tracker.ui.login.state

import com.example.tracker.mvi.states.AbstractEffect
import com.example.tracker.ui.login.LoginContract

sealed class LoginEffect : AbstractEffect<LoginContract.View>() {

    class Error(val error: String) : LoginEffect() {
        override fun handle(screen: LoginContract.View) {
            screen.showLoading()
        }
    }

    object Loading : LoginEffect()


}
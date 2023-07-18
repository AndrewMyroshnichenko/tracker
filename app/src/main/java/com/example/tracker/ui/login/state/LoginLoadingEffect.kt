package com.example.tracker.ui.login.state

import com.example.tracker.mvi.states.AbstractEffect
import com.example.tracker.ui.login.LoginContract

class LoginLoadingEffect : AbstractEffect<LoginContract.View>() {

    override fun handle(screen: LoginContract.View) {
        screen.showLoading()
    }

}
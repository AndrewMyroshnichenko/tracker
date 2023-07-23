package com.example.tracker.ui.login.state

import com.example.tracker.mvi.states.AbstractEffect
import com.example.tracker.ui.login.LoginContract

open class LoginEffect : AbstractEffect<LoginContract.View>() {
    //TODO: Realize this
    class ShowSuccessMessage(val messageId: Int?) : LoginEffect()

    object NavigateAfterSignIn : LoginEffect() {
        override fun visit(screen: LoginContract.View) {
            super.visit(screen)
            screen.nextScreen()
        }
    }

}
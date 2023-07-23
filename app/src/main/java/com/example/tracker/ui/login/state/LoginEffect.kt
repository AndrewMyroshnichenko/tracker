package com.example.tracker.ui.login.state

import com.example.tracker.mvi.states.AbstractEffect
import com.example.tracker.ui.login.LoginContract

open class LoginEffect : AbstractEffect<LoginContract.View>() {

    class ShowSuccessMessage(private val messageId: Int?) : LoginEffect() {
        override fun visit(screen: LoginContract.View) {
            super.visit(screen)
            screen.showLoginError(messageId)
        }
    }

    object NavigateAfterSignIn : LoginEffect() {
        override fun visit(screen: LoginContract.View) {
            super.visit(screen)
            screen.nextScreen()
        }
    }

}

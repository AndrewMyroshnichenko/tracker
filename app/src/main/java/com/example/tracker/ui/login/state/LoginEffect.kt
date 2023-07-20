package com.example.tracker.ui.login.state

import com.example.tracker.mvi.states.AbstractEffect
import com.example.tracker.ui.login.LoginContract

open class LoginEffect : AbstractEffect<LoginContract.View>() {
    class ShowSuccessMessage(val message: String?) : LoginEffect()

    object NavigateAfterSignIn : LoginEffect()

}
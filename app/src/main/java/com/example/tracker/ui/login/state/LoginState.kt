package com.example.tracker.ui.login.state

import com.example.tracker.mvi.states.AbstractState
import com.example.tracker.ui.login.LoginContract

open class LoginState : AbstractState<LoginContract.View, LoginState>(){
    data class LoginErrorState(val errorMessage: String?) : LoginState()
}

package com.example.tracker.ui.login

interface State

interface Event

interface Action

sealed class LoginState : State {

    data class SuccessSignIn(val email: String) : LoginState()
    object SuccessSignUp : LoginState()
    object SuccessResetPassword : LoginState()
    object SuccessSignOutState : LoginState()
    data class ShowError(val message: String?) : LoginState()

}

sealed class LoginEvent: Event {

    data class SingInEvent(val email: String, val password: String) : LoginEvent()
    data class SingUpEvent(val email: String, val password: String) : LoginEvent()
    object SingOutEvent : LoginEvent()
    class ForgotPasswordEvent(val email: String) : LoginEvent()
}

sealed class LoginAction: Action {

    data class DoSingInEvent(val email: String, val password: String) : LoginAction()
    data class DoSingUpEvent(val email: String, val password: String) : LoginAction()
    object DoSingOutEvent : LoginAction()
    class DoForgotPasswordEvent(val email: String) : LoginAction()

}
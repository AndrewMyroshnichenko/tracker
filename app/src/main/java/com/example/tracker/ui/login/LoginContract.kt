package com.example.tracker.ui.login

interface State

interface Event

interface Action

sealed class TrackerState : State {
    data class SuccessSignIn(val email: String) : TrackerState()
    object SuccessSignUp : TrackerState()
    object SuccessResetPassword : TrackerState()
    object SuccessSignOutState : TrackerState()
    data class ShowError(val message: String) : TrackerState()
}

sealed class TrackerEvent: Event {

    data class SingInEvent(val email: String, val password: String) : TrackerEvent()
    data class SingUpEvent(val email: String, val password: String) : TrackerEvent()
    object SingOutEvent : TrackerEvent()
    class ForgotPasswordEvent(val email: String) : TrackerEvent()
}

sealed class TrackerAction: Action {

    data class DoSingInEvent(val email: String, val password: String) : TrackerAction()
    data class DoSingUpEvent(val email: String, val password: String) : TrackerAction()
    object DoSingOutEvent : TrackerAction()
    class DoForgotPasswordEvent(val email: String) : TrackerAction()
}
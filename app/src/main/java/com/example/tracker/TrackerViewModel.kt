package com.example.tracker

import com.example.tracker.mvi.MviViewModel
import com.google.firebase.auth.FirebaseAuth

class TrackerViewModel : MviViewModel<TrackerState, TrackerEvent, TrackerAction>() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun dispatchEvent(event: TrackerEvent) {
        handleAction(eventToAction(event))
    }

    override fun eventToAction(event: TrackerEvent): TrackerAction {
        return when (event) {
            is TrackerEvent.SingInEvent -> TrackerAction.DoSingInEvent(event.email, event.password)
            is TrackerEvent.SingUpEvent -> TrackerAction.DoSingUpEvent(event.email, event.password)
            is TrackerEvent.SingOutEvent -> TrackerAction.DoSingOutEvent
            is TrackerEvent.ForgotPasswordEvent -> TrackerAction.DoForgotPasswordEvent(event.email)
        }
    }

    override fun handleAction(action: TrackerAction) {
        when (action) {
            is TrackerAction.DoSingInEvent -> signIn(action.email, action.password)
            is TrackerAction.DoSingUpEvent -> signUp(action.email, action.password)
            is TrackerAction.DoSingOutEvent -> signOut()
            is TrackerAction.DoForgotPasswordEvent -> forgotPassword(action.email)
        }
    }

    private fun signIn(userEmail: String, userPassword: String) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mState.postValue(TrackerState.SuccessSignIn(userEmail))
                } else {
                    mState.postValue(TrackerState.ShowError("Login failed!"))
                }
            }

    }


    private fun signUp(userEmail: String, userPassword: String) {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    mState.postValue(TrackerState.SuccessSignUp)
                } else {
                    mState.postValue(TrackerState.ShowError("Registration failed!"))
                }
            }

    }

    private fun signOut() {
        mAuth.signOut()
        mState.postValue(TrackerState.SuccessSignOutState)

    }

    private fun forgotPassword(userEmail: String) {
        mAuth.fetchSignInMethodsForEmail(userEmail)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.signInMethods.isNullOrEmpty()) {
                        mState.postValue(TrackerState.ShowError("This email doesn't exist"))
                    } else {
                        mAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {
                                    mState.postValue(TrackerState.SuccessResetPassword)
                                } else {
                                    mState.postValue(TrackerState.ShowError("Something went wrong, password wasn't reset"))
                                }
                            }
                    }
                } else {
                    mState.postValue(TrackerState.ShowError("Can't send request"))
                }
            }

    }
}
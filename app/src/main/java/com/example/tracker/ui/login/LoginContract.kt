package com.example.tracker.ui.login

import com.example.tracker.mvi.fragments.FragmentContract

class LoginContract {

    interface ViewModel : FragmentContract.ViewModel<View> {
        fun signIn(userEmail: String, userPassword: String)

        fun signUp(userEmail: String, userPassword: String)

        fun forgotPassword(userEmail: String)

        fun signOut()
    }

    interface View : FragmentContract.View {

        fun showMessage(message: String)

        fun proceedToNextScreen(actionId: Int?)

    }

    interface Host : FragmentContract.Host {

        fun proceedToNextScreen(actionId: Int?)

    }
}

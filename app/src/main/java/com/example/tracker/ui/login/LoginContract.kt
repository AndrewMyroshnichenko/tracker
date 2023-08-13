package com.example.tracker.ui.login

import com.example.tracker.mvi.fragments.FragmentContract

class LoginContract {

    interface ViewModel : FragmentContract.ViewModel<View> {

        fun signIn(userEmail: String, userPass: String)

        fun signUp(userEmail: String, userPassFirst: String, userPassSecond: String)

        fun forgotPassword(userEmail: String)

    }

    interface View : FragmentContract.View {

        fun showLoginError(messageId: Int)

        fun showPasswordError(messageId: Int)

        fun showPopUpError(messageId: Int?)

        fun proceedLoginToLocationScreen()

    }

    interface Host : FragmentContract.Host {

        fun proceedLoginToLocationScreen()

    }
}

package com.example.tracker.ui.splash

import com.example.tracker.mvi.fragments.FragmentContract

class SplashContract {

    interface ViewModel : FragmentContract.ViewModel<View>{
        fun isSignedIn(): Boolean
    }

    interface View : FragmentContract.View{
        fun nextScreen()
    }

    interface Host : FragmentContract.Host {
        fun proceedToLoginScreen()
        fun proceedToTrackerScreen()
    }
}
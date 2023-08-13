package com.example.tracker.ui.splash

import com.example.tracker.mvi.fragments.FragmentContract

class SplashContract {

    interface ViewModel : FragmentContract.ViewModel<View>

    interface View : FragmentContract.View{
        fun proceedToLoginScreen()
        fun proceedToTrackerScreen()
    }

    interface Host : FragmentContract.Host {
        fun proceedSplashToLoginScreen()
        fun proceedSplashToLocationScreen()
    }
}

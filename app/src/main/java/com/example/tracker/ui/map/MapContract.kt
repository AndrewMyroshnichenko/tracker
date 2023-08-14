package com.example.tracker.ui.map

import com.example.tracker.mvi.fragments.FragmentContract

class MapContract {

    interface ViewModel : FragmentContract.ViewModel<View>{

        fun singOut()

    }

    interface View : FragmentContract.View{

        fun proceedToLoginScreen()

    }

    interface Host : FragmentContract.Host {
        fun proceedLocationToLoginScreen()
    }

}

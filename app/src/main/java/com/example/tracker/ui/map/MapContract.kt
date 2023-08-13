package com.example.tracker.ui.map

import com.example.tracker.mvi.fragments.FragmentContract

class MapContract {

    interface ViewModel : FragmentContract.ViewModel<View>{

    }

    interface View : FragmentContract.View{

    }

    interface Host : FragmentContract.Host {
        fun proceedLocationToLoginScreen()
    }

}
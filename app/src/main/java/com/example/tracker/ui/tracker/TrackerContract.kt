package com.example.tracker.ui.tracker

import androidx.lifecycle.LiveData
import com.example.tracker.mvi.fragments.FragmentContract

class TrackerContract {

    interface ViewModel : FragmentContract.ViewModel<View>{

        fun singOut()

        fun buttonToggle()

        fun isGpsAvailable(): LiveData<Boolean>

    }

    interface View : FragmentContract.View{

        fun showTrackerState(serviceRunning: Boolean)

        fun startStopService(act: String)

        fun proceedToLoginScreen()
    }

    interface Host : FragmentContract.Host {
        fun proceedTrackerToLoginScreen()
    }
}

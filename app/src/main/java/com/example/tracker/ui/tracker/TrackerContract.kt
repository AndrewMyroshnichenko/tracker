package com.example.tracker.ui.tracker

import androidx.lifecycle.LiveData
import com.example.tracker.mvi.fragments.FragmentContract
import kotlinx.coroutines.flow.StateFlow

class TrackerContract {

    interface ViewModel : FragmentContract.ViewModel<View>{

        fun singOut()

        fun buttonToggle()

        fun isGpsAvailable(): StateFlow<Boolean>

    }

    interface View : FragmentContract.View{

        fun showTrackerState(serviceRunning: Boolean, isGpsEnable: Boolean)

        fun startStopService(act: String)

        fun proceedToLoginScreen()
    }

    interface Host : FragmentContract.Host {
        fun proceedTrackerToLoginScreen()
    }
}

package com.example.tracker.ui.tracker

import com.example.tracker.mvi.fragments.FragmentContract

class TrackerContract {

    interface ViewModel : FragmentContract.ViewModel<View>{

        fun singOut()

        fun startTrack(isProviderEnabled : Boolean)

        fun stopTrack(isProviderEnabled : Boolean)

    }

    interface View : FragmentContract.View{

        fun showTrackerState(serviceRunning: Boolean, gpsEnabled: Boolean)

        fun startStopService(act: String)

        fun nextScreen()
    }

    interface Host : FragmentContract.Host {

    }
}

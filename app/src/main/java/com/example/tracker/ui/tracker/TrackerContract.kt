package com.example.tracker.ui.tracker

import android.location.LocationManager
import com.example.tracker.mvi.fragments.FragmentContract

class TrackerContract {

    interface ViewModel : FragmentContract.ViewModel<View>{
        fun singOut()

        fun startTrack(locationManager: LocationManager)

        fun stopTrack()

    }

    interface View : FragmentContract.View{

        fun showTrackerIsOff()

        fun showTrackerCollectsLocation()

        fun showGpsIsOff()

        fun startStopService(act: String)

        fun nextScreen()
    }

    interface Host : FragmentContract.Host {


    }
}
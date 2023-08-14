package com.example.tracker.ui.map

import com.example.tracker.mvi.fragments.FragmentContract
import com.google.android.gms.maps.model.LatLng

class MapContract {

    interface ViewModel : FragmentContract.ViewModel<View>{

        fun getFilteredLocations(startDate: Long, endDate: Long)

        fun singOut()

    }

    interface View : FragmentContract.View{

        fun proceedToLoginScreen()

        fun showMapState(locationsList: List<LatLng>)

    }

    interface Host : FragmentContract.Host {
        fun proceedLocationToLoginScreen()
    }

}

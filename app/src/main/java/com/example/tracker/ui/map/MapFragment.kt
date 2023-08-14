package com.example.tracker.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tracker.R
import com.example.tracker.databinding.FragmentMapBinding
import com.example.tracker.mvi.fragments.HostedFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : HostedFragment<MapContract.View, MapContract.ViewModel, MapContract.Host>(),
    MapContract.View, View.OnClickListener {

    private var bind: FragmentMapBinding? = null

    override fun createModel(): MapContract.ViewModel {
        val viewModel: MapViewModel by viewModels()
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        bind = FragmentMapBinding.inflate(layoutInflater)
        return (bind?.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind?.ibLogout?.setOnClickListener(this)
        bind?.btTimePicker?.setOnClickListener(this)
    }

    override fun proceedToLoginScreen() {
        fragmentHost?.proceedLocationToLoginScreen()
    }

    override fun showMapState(locationsList: List<LatLng>) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            renderMarks(googleMap, locationsList)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_logout -> model?.singOut()
            R.id.bt_time_picker -> showDateRangePicker()
        }
    }

    private fun showDateRangePicker() {

        val picker = MaterialDatePicker.Builder
            .dateRangePicker()
            .setTheme(R.style.CustomMaterialDatePickerTheme)
            .build()

        picker.addOnPositiveButtonClickListener { selection ->
            model?.getFilteredLocations(selection.first, selection.second)
        }

        picker.show(childFragmentManager, null)

    }

    private fun renderMarks(mMap: GoogleMap, list: List<LatLng>) {
        mMap.clear()
        mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(list)
                .color(R.color.way_color)
        )
    }


}

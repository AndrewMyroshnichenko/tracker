package com.example.tracker.ui.tracker

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.tracker.R
import com.example.tracker.databinding.FragmentTrackerBinding
import com.example.tracker.models.gps.LocationService
import com.example.tracker.mvi.fragments.HostedFragment
import com.example.tracker.ui.tracker.state.TrackerState


class TrackerFragment :
    HostedFragment<TrackerContract.View, TrackerContract.ViewModel, TrackerContract.Host>(),
    TrackerContract.View, View.OnClickListener {

    private var bind: FragmentTrackerBinding? = null

    override fun createModel(): TrackerContract.ViewModel {
        return ViewModelProvider(
            this, TrackerViewModelFactory()
        )[TrackerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        bind = FragmentTrackerBinding.inflate(layoutInflater)
        return (bind?.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind?.ibSignout?.setOnClickListener(this)
        bind?.btStartStop?.setOnClickListener(this)
        model?.stopTrack()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_start_stop -> toggleTrack()
            R.id.ib_signout -> model?.singOut()
        }
    }

    private fun toggleTrack() {
        if (model?.getStateObservable()?.value is TrackerState.TrackerIsOffState || model?.getStateObservable()?.value is TrackerState.TrackerIsOffState) {
            model?.startTrack(activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        } else {
            model?.stopTrack()
        }
    }

    override fun startStopService(act: String) {
        Intent(requireContext(), LocationService::class.java).apply {
            action = act
            requireContext().startService(this)
        }
    }

    override fun nextScreen() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.action_trackerFragment_to_loginFragment)
    }

    private fun setViewsProperties(
        btText: String,
        btTextColor: Int,
        btBackgroundColor: Int,
        pbGradient: Drawable?,
        tvStateTracker: String,
        tvHelperText: String,
        imgTrackerIndicator: Int
    ) {
        bind?.btStartStop?.text = btText
        bind?.btStartStop?.setTextColor(btTextColor)
        bind?.btStartStop?.setBackgroundColor(btBackgroundColor)
        bind?.trackerBar?.progressDrawable = pbGradient
        bind?.trackerBar?.indeterminateDrawable = pbGradient
        bind?.tvStateTracker?.text = tvStateTracker
        bind?.tvHelper?.text = tvHelperText
        bind?.imgTrackerIndicator?.setImageResource(imgTrackerIndicator)
    }

    override fun showTrackerIsOff() {
        setViewsProperties(
            btText = resources.getString(R.string.start),
            btTextColor = ContextCompat.getColor(requireContext(), R.color.white),
            btBackgroundColor = ContextCompat.getColor(requireContext(), R.color.main),
            pbGradient = ContextCompat.getDrawable(requireActivity(), R.drawable.pb_stop_gradient),
            tvStateTracker = resources.getString(R.string.tracker_off),
            tvHelperText = "",
            imgTrackerIndicator = R.drawable.img_tracker_is_off
        )
    }

    override fun showTrackerCollectsLocation() {
        setViewsProperties(
            btText = resources.getString(R.string.stop),
            btTextColor = ContextCompat.getColor(requireContext(), R.color.main),
            btBackgroundColor = ContextCompat.getColor(requireContext(), R.color.white),
            pbGradient = ContextCompat.getDrawable(requireActivity(), R.drawable.pb_gradient),
            tvStateTracker = resources.getString(R.string.tracker),
            tvHelperText = resources.getString(R.string.collects_locations),
            imgTrackerIndicator = R.drawable.img_tracker_collects_locations
        )
    }

    override fun showGpsIsOff() {
        setViewsProperties(
            btText = resources.getString(R.string.start),
            btTextColor = ContextCompat.getColor(requireContext(), R.color.white),
            btBackgroundColor = ContextCompat.getColor(requireContext(), R.color.main),
            pbGradient = ContextCompat.getDrawable(
                requireActivity(), R.drawable.pb_error_gradient
            ),
            tvStateTracker = resources.getString(R.string.gps_off),
            tvHelperText = resources.getString(R.string.tracker_cant_collect_locations),
            imgTrackerIndicator = R.drawable.img_gps_is_off
        )
    }
}

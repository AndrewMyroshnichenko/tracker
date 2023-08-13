package com.example.tracker.ui.tracker

import android.Manifest
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.tracker.R
import com.example.tracker.bg.LocationService
import com.example.tracker.databinding.FragmentTrackerBinding
import com.example.tracker.mvi.fragments.HostedFragment
import com.example.tracker.ui.tracker.state.TrackerState
import com.example.tracker.utils.PermissionsUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackerFragment :
    HostedFragment<TrackerContract.View, TrackerContract.ViewModel, TrackerContract.Host>(),
    TrackerContract.View, View.OnClickListener {

    private var bind: FragmentTrackerBinding? = null

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                return@registerForActivityResult
            }
            view?.let {
                Snackbar.make(
                    it, getString(R.string.permissions_doesn_t_assigned), Snackbar.LENGTH_LONG
                ).show()
            }
        }

    override fun createModel(): TrackerContract.ViewModel {
        val viewModel: TrackerViewModel by viewModels()
        return viewModel
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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_start_stop -> toggleTrack()
            R.id.ib_signout -> model?.singOut()
        }
    }

    private fun toggleTrack() {
        val state = model?.getStateObservable()?.value as TrackerState

        if (state.serviceRunning) {
            startStopService(LocationService.ACTION_STOP)
        } else {
            if (PermissionsUtil.hasLocationPermission(requireContext())) {
                startStopService(LocationService.ACTION_START)
            } else {
                requestLocationPermission()
            }
        }
    }

    override fun startStopService(act: String) {
        Intent(requireContext(), LocationService::class.java).apply {
            action = act
            requireContext().startService(this)
        }
    }

    override fun proceedToLoginScreen() {
        fragmentHost?.proceedTrackerToLoginScreen()
    }

    override fun showTrackerState(
        serviceRunning: Boolean,
        isGpsEnable: Boolean,
        locationsCounter: Int
    ) {
        if (!isGpsEnable && serviceRunning) {
            setViewsProperties(
                btText = resources.getString(R.string.stop),
                btTextColor = ContextCompat.getColor(requireContext(), R.color.main),
                btBackgroundColor = ContextCompat.getColor(requireContext(), R.color.white),
                pbGradient = ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.pb_error_gradient
                ),
                tvStateTracker = resources.getString(R.string.gps_off),
                tvHelperText = resources.getString(R.string.tracker_cant_collect_locations),
                tvCounter = resources.getString(R.string.counter, locationsCounter.toString()),
                imgTrackerIndicator = R.drawable.img_gps_is_off
            )
        } else {
            if (serviceRunning) {
                setViewsProperties(
                    btText = resources.getString(R.string.stop),
                    btTextColor = ContextCompat.getColor(requireContext(), R.color.main),
                    btBackgroundColor = ContextCompat.getColor(requireContext(), R.color.white),
                    pbGradient = ContextCompat.getDrawable(
                        requireActivity(), R.drawable.pb_gradient
                    ),
                    tvStateTracker = resources.getString(R.string.tracker),
                    tvHelperText = resources.getString(R.string.collects_locations),
                    tvCounter = resources.getString(R.string.counter, locationsCounter.toString()),
                    imgTrackerIndicator = R.drawable.img_tracker_collects_locations
                )
            } else {
                setViewsProperties()
            }
        }
    }

    private fun setViewsProperties(
        btText: String = resources.getString(R.string.start),
        btTextColor: Int = ContextCompat.getColor(requireContext(), R.color.white),
        btBackgroundColor: Int = ContextCompat.getColor(requireContext(), R.color.main),
        pbGradient: Drawable? = ContextCompat.getDrawable(
            requireActivity(), R.drawable.pb_stop_gradient
        ),
        tvStateTracker: String = resources.getString(R.string.tracker_off),
        tvHelperText: String = "",
        tvCounter: String = "",
        imgTrackerIndicator: Int = R.drawable.img_tracker_is_off
    ) {
        bind?.btStartStop?.text = btText
        bind?.btStartStop?.setTextColor(btTextColor)
        bind?.btStartStop?.setBackgroundColor(btBackgroundColor)
        bind?.trackerBar?.progressDrawable = pbGradient
        bind?.trackerBar?.indeterminateDrawable = pbGradient
        bind?.tvStateTracker?.text = tvStateTracker
        bind?.tvHelper?.text = tvHelperText
        bind?.tvCounter?.text = tvCounter
        bind?.imgTrackerIndicator?.setImageResource(imgTrackerIndicator)
    }

    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ||
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", requireActivity().packageName, null)
            startActivity(intent)
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }
}

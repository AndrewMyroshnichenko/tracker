package com.example.tracker.ui.tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tracker.R
import com.example.tracker.databinding.FragmentTrackerBinding
import com.google.firebase.auth.FirebaseAuth


class TrackerFragment : Fragment(), View.OnClickListener {

    private var bind: FragmentTrackerBinding? = null
    private var mAuth: FirebaseAuth? = null
    //private var viewModel: LoginViewModel? = vm

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAuth = FirebaseAuth.getInstance()
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

            //R.id.bt_start_stop -> mAuth?.signOut()
            R.id.ib_signout -> mAuth?.signOut()
        }
    }
    /*

        private fun toggleTrack() {
            if (bind?.btStartStop?.text == resources.getString(R.string.start)) {
                startTrack()
            } else {
                stopTrack()
            }
        }

        private fun startTrack() {

            val locationManager =
                activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            startStopService(LocationService.ACTION_START)

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                setViewsProperties(
                    btText = resources.getString(R.string.stop),
                    btTextColor = ContextCompat.getColor(requireContext(), R.color.main),
                    btBackgroundColor = ContextCompat.getColor(requireContext(), R.color.white),
                    pbGradient = ContextCompat.getDrawable(requireActivity(), R.drawable.pb_gradient),
                    tvStateTracker = resources.getString(R.string.tracker),
                    tvHelperText = resources.getString(R.string.collects_locations),
                    imgTrackerIndicator = R.drawable.img_tracker_collects_locations
                )
            } else {
                setViewsProperties(
                    btText = resources.getString(R.string.start),
                    btTextColor = ContextCompat.getColor(requireContext(), R.color.white),
                    btBackgroundColor = ContextCompat.getColor(requireContext(), R.color.main),
                    pbGradient = ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.pb_error_gradient
                    ),
                    tvStateTracker = resources.getString(R.string.gps_off),
                    tvHelperText = resources.getString(R.string.tracker_cant_collect_locations),
                    imgTrackerIndicator = R.drawable.img_gps_is_off
                )
            }
        }

        private fun stopTrack() {
            startStopService(LocationService.ACTION_STOP)
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

        private fun logOut() {
            //viewModel?.send(LogOutEvent())
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

        private fun startStopService(act: String) {
            Intent(requireContext(), LocationService::class.java).apply {
                action = act
                requireContext().startService(this)
            }
        }

    */

}
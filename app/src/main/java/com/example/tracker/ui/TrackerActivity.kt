package com.example.tracker.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.tracker.R
import com.example.tracker.databinding.ActivityTrackerBinding
import com.example.tracker.ui.login.LoginContract
import com.example.tracker.ui.splash.SplashContract

class TrackerActivity : AppCompatActivity(), SplashContract.Host, LoginContract.Host {

    private var navController: NavController? = null
    private var bind: ActivityTrackerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
        bind = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(bind?.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun proceedLoginToTrackerScreen() {
        navController?.navigate(R.id.action_loginFragment_to_trackerFragment)
    }

    override fun proceedToLoginScreen() {
        navController?.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun proceedToTrackerScreen() {
        navController?.navigate(R.id.action_splashFragment_to_trackerFragment)
    }
}

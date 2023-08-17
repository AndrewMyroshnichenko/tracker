package com.example.tracker.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.tracker.R
import com.example.tracker.databinding.ActivityTrackerBinding
import com.example.tracker.ui.login.LoginContract
import com.example.tracker.ui.splash.SplashContract
import com.example.tracker.ui.tracker.TrackerContract
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackerActivity : AppCompatActivity(), SplashContract.Host, LoginContract.Host,
    TrackerContract.Host {

    private var navController: NavController? = null
    private var bind: ActivityTrackerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //FirebaseApp.initializeApp(this)
        bind = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(bind?.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_tracker)
    }

    override fun proceedAuthToMainScreen() {
        navController?.navigate(
            R.id.action_loginFragment_to_trackerFragment, null,
            NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build()
        )
    }

    override fun proceedSplashToAuthScreen() {
        navController?.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun proceedSplashToMainScreen() {
        Log.d("BUG","Tracker navigate")
        navController?.navigate(
            R.id.action_splashFragment_to_trackerFragment, null,
            NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build()
        )
    }

    override fun proceedLocationToLoginScreen() {
        navController?.navigate(R.id.action_trackerFragment_to_loginFragment)
    }
}

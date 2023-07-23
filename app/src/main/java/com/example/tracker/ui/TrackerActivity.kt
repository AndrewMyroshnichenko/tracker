package com.example.tracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tracker.R
import com.example.tracker.databinding.ActivityTrackerBinding
import com.example.tracker.ui.login.LoginContract

class TrackerActivity : AppCompatActivity(), LoginContract.Host{

    private var navController: NavController? = null
    private var bind: ActivityTrackerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


}

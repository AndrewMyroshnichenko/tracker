package com.example.tracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tracker.R
import com.example.tracker.databinding.ActivityMapBinding
import com.example.auth.login.LoginContract
import com.example.tracker.ui.map.MapContract
import com.example.auth.splash.SplashContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), SplashContract.Host, LoginContract.Host, MapContract.Host {

    private var navController: NavController? = null
    private var bind: ActivityMapBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMapBinding.inflate(layoutInflater)
        setContentView(bind?.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_map)
    }

    override fun proceedAuthToMainScreen() {
        navController?.navigate(R.id.action_loginFragment_to_mapFragment)
    }

    override fun proceedSplashToAuthScreen() {
        navController?.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun proceedSplashToMainScreen() {
        navController?.navigate(R.id.action_splashFragment_to_mapFragment)
    }

    override fun proceedLocationToLoginScreen() {
        navController?.navigate(R.id.action_mapFragment_to_loginFragment)
    }
}

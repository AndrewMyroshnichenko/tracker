package com.example.tracker.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tracker.R
import com.example.tracker.mvi.fragments.HostedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment :
    HostedFragment<SplashContract.View, SplashContract.ViewModel, SplashContract.Host>(),
    SplashContract.View {

    override fun createModel(): SplashContract.ViewModel {
        val viewModel: SplashViewModel by viewModels()
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.d("BUG","Splash created")
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun proceedToAuthScreen() {
        fragmentHost?.proceedSplashToAuthScreen()
    }

    override fun proceedToMainScreen() {
        fragmentHost?.proceedSplashToMainScreen()
        Log.d("BUG","Splash fragment nav")
    }
}

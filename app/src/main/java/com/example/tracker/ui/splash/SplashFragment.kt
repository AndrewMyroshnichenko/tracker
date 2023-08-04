package com.example.tracker.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tracker.R
import com.example.tracker.mvi.fragments.HostedFragment

class SplashFragment :
    HostedFragment<SplashContract.View, SplashContract.ViewModel, SplashContract.Host>(),
    SplashContract.View {

    val splashViewModel : SplashViewModel by viewModels()

    override fun createModel(): SplashContract.ViewModel {

        return splashViewModel
/*        return ViewModelProvider(
            this, SplashViewModelFactory()
        )[SplashViewModel::class.java]*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun proceedToLoginScreen() {
        fragmentHost?.proceedSplashToLoginScreen()
    }

    override fun proceedToTrackerScreen() {
        fragmentHost?.proceedSplashToTrackerScreen()
    }
}

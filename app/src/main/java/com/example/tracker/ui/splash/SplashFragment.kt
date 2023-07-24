package com.example.tracker.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.tracker.R
import com.example.tracker.mvi.fragments.HostedFragment


class SplashFragment :
    HostedFragment<SplashContract.View, SplashContract.ViewModel, SplashContract.Host>(),
    SplashContract.View {

    override fun createModel(): SplashContract.ViewModel {
        return ViewModelProvider(
            this, SplashViewModelFactory()
        )[SplashViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextScreen()
    }

    override fun nextScreen() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        if (model?.isSignedIn() == true) {
            navController.navigate(R.id.action_splashFragment_to_trackerFragment)
        } else {
            navController.navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}

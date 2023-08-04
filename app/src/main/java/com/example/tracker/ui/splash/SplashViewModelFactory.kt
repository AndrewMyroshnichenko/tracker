package com.example.tracker.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tracker.models.auth.FbAuth

class SplashViewModelFactory : ViewModelProvider.Factory {

   @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return SplashViewModel(FbAuth()) as T
   }
}

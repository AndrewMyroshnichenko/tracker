package com.example.tracker.ui.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tracker.models.FirebaseManager

class TrackerViewModelFactory: ViewModelProvider.Factory  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrackerViewModel(FirebaseManager()) as T
    }
}
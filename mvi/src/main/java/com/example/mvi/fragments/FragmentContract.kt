package com.example.mvi.fragments

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData

class FragmentContract {

    interface ViewModel<V> : LifecycleObserver {
        fun onStateChanged(event: Lifecycle.Event)
        fun getStateObservable(): LiveData<com.example.mvi.states.ScreenState<V>>
        fun getEffectObservable(): LiveData<com.example.mvi.states.ScreenState<V>>
    }

    interface View
    interface Host

}

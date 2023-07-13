package com.example.tracker.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class MviViewModel<STATE : State, EVENT : Event, ACTION : Action> : ViewModel() {

    protected val mState = MutableLiveData<STATE>()

    val state: LiveData<STATE> get() = mState


    abstract fun eventToAction(event: EVENT): ACTION

    abstract fun handleAction(action: ACTION)

}
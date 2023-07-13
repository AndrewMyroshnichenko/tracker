package com.example.tracker.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tracker.Action
import com.example.tracker.Event
import com.example.tracker.State

abstract class MviViewModel<STATE : State, EVENT : Event, ACTION : Action> : ViewModel() {

    protected val mState = MutableLiveData<STATE>()

    val state: LiveData<STATE> get() = mState


    abstract fun eventToAction(event: EVENT): ACTION

    abstract fun handleAction(action: ACTION)

}
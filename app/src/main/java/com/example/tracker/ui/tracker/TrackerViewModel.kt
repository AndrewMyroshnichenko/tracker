package com.example.tracker.ui.tracker

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.bus.StatusManager
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.tracker.state.TrackerEffect
import com.example.tracker.ui.tracker.state.TrackerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val authNetwork: Auth,
    private val gpsStateCache: StatusManager
) : MviViewModel<TrackerContract.View, TrackerState>(), TrackerContract.ViewModel {

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            viewModelScope.launch {
                combine(
                    gpsStateCache.getServiceStatus(),
                    gpsStateCache.getGpsStatus()
                ) { servStatus, gpsStatus ->
                    TrackerState(servStatus, gpsStatus)
                }.collect { newState ->
                    setState(newState)
                }
            }
        }
    }

    override fun singOut() {
        authNetwork.signOut()
        setEffect(TrackerEffect.NavigateAfterLogOut())
    }
}

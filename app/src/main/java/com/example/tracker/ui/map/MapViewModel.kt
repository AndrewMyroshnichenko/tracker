package com.example.tracker.ui.map

import com.example.tracker.models.auth.Auth
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.map.state.MapEffect
import com.example.tracker.ui.map.state.MapState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val authNetwork: Auth
) : MviViewModel<MapContract.View, MapState>(), MapContract.ViewModel {


    override fun singOut() {
        authNetwork.signOut()
        setEffect(MapEffect.NavigateAfterLogOut())
    }


}
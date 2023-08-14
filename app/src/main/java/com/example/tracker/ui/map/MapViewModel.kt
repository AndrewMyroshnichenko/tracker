package com.example.tracker.ui.map

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.locations.LocationsRepository
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.map.state.MapEffect
import com.example.tracker.ui.map.state.MapState
import com.example.tracker.ui.tracker.state.TrackerState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val authNetwork: Auth,
    private val locationsRepository: LocationsRepository
) : MviViewModel<MapContract.View, MapState>(), MapContract.ViewModel {

    private var lastLocationTime = 0L

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            viewModelScope.launch {
                lastLocationTime = locationsRepository.getMapLocations(lastLocationTime)
                    .maxByOrNull { it.time.toLong() }?.time?.toLong() ?: 0L
            }
        }
    }

    override fun getFilteredLocations(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            val list = locationsRepository.getMapLocations(lastLocationTime)
                .filter { it.time.toLong() in startDate..endDate }
                .sortedBy { it.time.toLong() }
                .map { LatLng(it.latitude.toDouble(), it.longitude.toDouble()) }
            setState(MapState(list))
        }
    }


    override fun singOut() {
        authNetwork.signOut()
        viewModelScope.launch {
            locationsRepository.clearLocations()
        }
        setEffect(MapEffect.NavigateAfterLogOut())
    }

}

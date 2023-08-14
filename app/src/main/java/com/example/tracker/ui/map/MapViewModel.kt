package com.example.tracker.ui.map

import androidx.lifecycle.viewModelScope
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.locations.LocationsRepository
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.map.state.MapEffect
import com.example.tracker.ui.map.state.MapState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val authNetwork: Auth,
    private val locationsRepository: LocationsRepository
) : MviViewModel<MapContract.View, MapState>(), MapContract.ViewModel {

    override fun getFilteredLocations(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            val list = locationsRepository.getMapLocations()
                .filter { it.time.toLong() in startDate..endDate }
            setState(MapState(list))
        }
    }

    override fun singOut() {
        authNetwork.signOut()
        viewModelScope.launch {
            locationsRepository.clearLocations()
            setEffect(MapEffect.NavigateAfterLogOut())
        }
    }

}

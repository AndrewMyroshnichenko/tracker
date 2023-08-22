package com.example.tracker.ui.map

import androidx.lifecycle.viewModelScope
import com.example.tracker.R
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.locations.LocationsRepository
import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.map.state.MapEffect
import com.example.tracker.ui.map.state.MapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val authNetwork: Auth,
    private val locationsRepository: LocationsRepository
) : MviViewModel<MapContract.View, MapState>(), MapContract.ViewModel {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        setEffect(MapEffect.ShowMessage(R.string.download_failed))
    }

    override fun getFilteredLocations(startDate: Long, endDate: Long) {
        viewModelScope.launch(exceptionHandler) {
            val list = locationsRepository.getMapLocations(startDate, endDate)
            if (list.isEmpty()){
                setEffect(MapEffect.ShowMessage(R.string.any_locations))
            }else{
                setState(MapState(list))
            }

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

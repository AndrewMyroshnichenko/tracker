package com.example.tracker.ui.map

import androidx.lifecycle.Lifecycle
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

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_CREATE) {
            getFilteredLocations(startDate = getTodayTime())
        }
    }

    override fun getFilteredLocations(startDate: Long, endDate: Long) {
        viewModelScope.launch(exceptionHandler) {
            setState(MapState(listOf(), true))
            val list  = locationsRepository.getMapLocations(startDate, endDate)
            if (list.isEmpty()){
                setEffect(MapEffect.ShowMessage(R.string.any_locations))
            }else{
                setState(MapState(list, false))
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

    private fun getTodayTime(): Long {
        return System.currentTimeMillis() - MILLISECONDS_PER_DAY
    }

    companion object{
        const val MILLISECONDS_PER_DAY = 86_400_000L
    }

}

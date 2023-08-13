package com.example.tracker.ui.map

import com.example.tracker.mvi.MviViewModel
import com.example.tracker.ui.map.state.MapState

class MapViewModel : MviViewModel<MapContract.View, MapState>(), MapContract.ViewModel {
}
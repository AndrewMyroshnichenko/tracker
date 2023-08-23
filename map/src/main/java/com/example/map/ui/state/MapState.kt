package com.example.map.ui.state

import com.example.models.locations.Location
import com.example.mvi.states.AbstractState
import com.example.map.ui.MapContract

class MapState(
    private val locations: List<Location>,
    private val isGetLocationsRunning: Boolean,
) : AbstractState<MapContract.View, MapState>() {
    override fun visit(screen: MapContract.View) {
        screen.showMapState(locations, isGetLocationsRunning)
    }
}


package com.example.tracker.ui.map.state

import com.example.tracker.models.locations.Location
import com.example.mvi.states.AbstractState
import com.example.tracker.ui.map.MapContract

class MapState(
    private val locations: List<Location>,
    private val isGetLocationsRunning: Boolean,
) : AbstractState<MapContract.View, MapState>() {
    override fun visit(screen: MapContract.View) {
        screen.showMapState(locations, isGetLocationsRunning)
    }
}


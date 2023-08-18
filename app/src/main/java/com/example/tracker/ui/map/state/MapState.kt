package com.example.tracker.ui.map.state


import com.example.tracker.models.locations.Location
import com.example.tracker.mvi.states.AbstractState
import com.example.tracker.ui.map.MapContract

class MapState(private val locations: List<Location>) : AbstractState<MapContract.View, MapState>() {
    override fun visit(screen: MapContract.View) {
        screen.showMapState(locations)
    }
}

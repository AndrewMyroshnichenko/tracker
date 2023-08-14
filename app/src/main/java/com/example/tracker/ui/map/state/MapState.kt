package com.example.tracker.ui.map.state

import com.example.tracker.mvi.states.AbstractState
import com.example.tracker.ui.map.MapContract
import com.google.android.gms.maps.model.LatLng

class MapState(val locations: List<LatLng>) : AbstractState<MapContract.View, MapState>() {
    override fun visit(screen: MapContract.View) {
        screen.showMapState(locations)
    }
}

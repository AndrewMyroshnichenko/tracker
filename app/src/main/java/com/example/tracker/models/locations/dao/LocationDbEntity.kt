package com.example.tracker.models.locations.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tracker.models.locations.Location

@Entity(LocationDbEntity.LOCATIONS_TABLE_NAME)
data class LocationDbEntity(
    @PrimaryKey
    val time: String,
    val latitude: String,
    val longitude: String
) {

    fun toLocation(): Location = Location(
        time = time,
        latitude = latitude,
        longitude = longitude
    )

    companion object {

        const val LOCATIONS_TABLE_NAME = "locations"

        fun toLocationDbEntity(location: Location) = LocationDbEntity(
            time = location.time,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }
}

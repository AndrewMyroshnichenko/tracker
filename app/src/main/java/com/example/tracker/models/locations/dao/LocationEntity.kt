package com.example.tracker.models.locations.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tracker.models.locations.Location

@Entity(LocationEntity.LOCATIONS_TABLE_NAME)
data class LocationEntity(
    @PrimaryKey
    val time: String,
    val ownerId: String,
    val latitude: String,
    val longitude: String
) {

    fun toLocation(): Location = Location(
        time = time,
        ownerId = ownerId,
        latitude = latitude,
        longitude = longitude
    )

    companion object {

        const val LOCATIONS_TABLE_NAME = "locations"

        fun toLocationEntity(location: Location) = LocationEntity(
            time = location.time,
            ownerId = location.ownerId,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }
}

package com.example.tracker.data.locations.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tracker.data.locations.Location

@Entity(tableName = "marks")
data class LocationDbEntity(
    @PrimaryKey
    val time: String,
    val email: String,
    val latitude: String,
    val longitude: String
) {

    fun toLocation(): Location = Location(
        time = time,
        email = email,
        latitude = latitude,
        longitude = longitude
    )

    companion object {
        fun toLocationDbEntity(location: Location) = LocationDbEntity(
            time = location.time,
            email = location.email,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }
}
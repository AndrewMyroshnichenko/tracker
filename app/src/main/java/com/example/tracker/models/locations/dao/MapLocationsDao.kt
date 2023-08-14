package com.example.tracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MapLocationsDao {

    @Upsert(entity = MapLocationEntity::class)
    suspend fun upsertLocation(loadedLocationEntity: MapLocationEntity)

    @Query("SELECT * FROM mapLocations WHERE ownerId = :ownerId")
    suspend fun getLocations(ownerId: String): List<MapLocationEntity>

}
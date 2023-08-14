package com.example.tracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MapLocationsDao {

    @Insert(entity = MapLocationEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveLocations(locations: List<MapLocationEntity>)

    @Query("SELECT * FROM mapLocations WHERE ownerId = :ownerId")
    suspend fun getLocations(ownerId: String): List<MapLocationEntity>

}
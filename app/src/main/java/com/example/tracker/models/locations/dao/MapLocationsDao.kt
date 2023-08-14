package com.example.tracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MapLocationsDao {

    @Insert(entity = MapLocationEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocations(locations: List<MapLocationEntity>)

    @Query("DELETE FROM mapLocations")
    suspend fun deleteAllLocations()

    @Query("SELECT * FROM mapLocations ORDER BY time ASC")
    suspend fun getLocations(): List<MapLocationEntity>

}

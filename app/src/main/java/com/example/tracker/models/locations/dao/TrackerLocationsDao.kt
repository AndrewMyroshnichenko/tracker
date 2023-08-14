package com.example.tracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TrackerLocationsDao {

    @Upsert(entity = TrackerLocationEntity::class)
    suspend fun upsertLocation(locationEntity: TrackerLocationEntity)

    @Delete(entity = TrackerLocationEntity::class)
    suspend fun deleteLocation(locationEntity: TrackerLocationEntity)

    @Query("SELECT * FROM trackerLocations WHERE ownerId = :ownerId")
    suspend fun getLocations(ownerId: String): List<TrackerLocationEntity>

}

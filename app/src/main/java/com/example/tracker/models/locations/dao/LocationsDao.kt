package com.example.tracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LocationsDao {

    @Upsert(entity = LocationEntity::class)
    suspend fun upsertLocation(markDbEntity: LocationEntity)

    @Delete(entity = LocationEntity::class)
    suspend fun deleteLocation(markDbEntity: LocationEntity)

    @Query("SELECT * FROM locations WHERE ownerId = :ownerId")
    suspend fun getMarks(ownerId: String): List<LocationEntity>

}

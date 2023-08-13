package com.example.tracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LocationsDao {

    @Upsert(entity = LocationEntity::class)
    suspend fun upsertMark(markDbEntity: LocationEntity)

    @Delete(entity = LocationEntity::class)
    suspend fun deleteMark(markDbEntity: LocationEntity)

    @Query("SELECT * FROM locations WHERE ownerId = :ownerId")
    suspend fun getMarks(ownerId: String): List<LocationEntity>

}

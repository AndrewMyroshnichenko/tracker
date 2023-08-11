package com.example.tracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocationsDao {

    @Insert(entity = LocationEntity::class)
    suspend fun insertMark(markDbEntity: LocationEntity)

    @Delete(entity = LocationEntity::class)
    suspend fun deleteMark(markDbEntity: LocationEntity)

    @Query("SELECT * FROM locations")
    suspend fun getMarks(): List<LocationEntity>

}

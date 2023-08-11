package com.example.tracker.models.locations.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocationsDao {

    @Insert(entity = LocationDbEntity::class)
    suspend fun insertMark(markDbEntity: LocationDbEntity)

    @Delete(entity = LocationDbEntity::class)
    suspend fun deleteMark(markDbEntity: LocationDbEntity)

    @Query("SELECT * FROM locations")
    suspend fun getMarks(): List<LocationDbEntity>

}

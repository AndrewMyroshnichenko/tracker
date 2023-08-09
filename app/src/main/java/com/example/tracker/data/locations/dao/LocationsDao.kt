package com.example.tracker.data.locations.dao

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

    @Query("SELECT * FROM marks WHERE email = :email")
    suspend fun getMarks(email: String): List<LocationDbEntity>

}
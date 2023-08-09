package com.example.tracker.data.auth.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert(UserDbEntity::class)
    suspend fun upsertUser(user: UserDbEntity)

    @Query("SELECT * FROM users")
    suspend fun getCurrentUser(): UserDbEntity?

}
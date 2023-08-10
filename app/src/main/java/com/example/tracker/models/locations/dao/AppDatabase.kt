package com.example.tracker.models.locations.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tracker.models.auth.dao.UserDao
import com.example.tracker.models.auth.dao.UserDbEntity

@Database(
    version = 1,
    entities = [LocationDbEntity::class, UserDbEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMarkDao(): LocationsDao

    abstract fun getUserDao(): UserDao

    companion object {

        const val DATABASE_NAME = "tracker"

        fun getDB(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}

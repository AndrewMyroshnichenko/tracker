package com.example.tracker.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tracker.models.locations.dao.LocationEntity
import com.example.tracker.models.locations.dao.LocationsDao

@Database(
    version = 1,
    entities = [LocationEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMarkDao(): LocationsDao

    companion object {

        const val DATABASE_NAME = "tracker"

        fun getDB(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}

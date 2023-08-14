package com.example.tracker.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tracker.models.locations.dao.MapLocationEntity
import com.example.tracker.models.locations.dao.MapLocationsDao
import com.example.tracker.models.locations.dao.TrackerLocationEntity
import com.example.tracker.models.locations.dao.TrackerLocationsDao

@Database(
    version = 1,
    entities = [TrackerLocationEntity::class, MapLocationEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLocationsDao(): TrackerLocationsDao

    abstract fun getLoadedLocationsDao(): MapLocationsDao

    companion object {

        const val DATABASE_NAME = "tracker"

        fun getDB(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}

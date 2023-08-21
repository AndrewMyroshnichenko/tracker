package com.example.tracker.models.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStorePrefs(private val context: Context) : Prefs {
    private val gson = Gson()
    private val Context.mapsDataStore: DataStore<Preferences> by preferencesDataStore(name = "maps")
    private val Context.trackerDataStore: DataStore<Preferences> by preferencesDataStore(name = "tracker")
    private val KEY_TRACKER_STATUS = booleanPreferencesKey("tracker_status")
    private val KEY_RANGES = stringPreferencesKey("ranges")


    override suspend fun getLoadedRanges(): List<Pair<Long, Long>> {
        return context.mapsDataStore.data.map {
            return@map gson.fromJson<List<Pair<Long, Long>>>(
                it[KEY_RANGES] ?: "[]",
                object : TypeToken<List<Pair<Long, Long>>>() {}.type
            )
        }.first()
    }

    override suspend fun putLoadedRanges(ranges: List<Pair<Long, Long>>) {
        context.mapsDataStore.edit { it[KEY_RANGES] = gson.toJson(ranges) }
    }

    override suspend fun clear() {
        context.mapsDataStore.edit { it[KEY_RANGES] = "[]" }
    }

    override suspend fun getTrackerStatus(): Boolean {
        return context.trackerDataStore.data.map { it[KEY_TRACKER_STATUS] ?: false }.first()
    }

    override suspend fun putTrackerStatus(trackerStatus: Boolean) {
        context.trackerDataStore.edit { it[KEY_TRACKER_STATUS] = trackerStatus }
    }
}

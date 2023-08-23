package com.example.models.prefs

interface TrackerPrefs {
    suspend fun getTrackerStatus(): Boolean
    suspend fun putTrackerStatus(trackerStatus: Boolean)
}

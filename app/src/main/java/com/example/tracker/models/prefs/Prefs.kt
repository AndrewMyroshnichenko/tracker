package com.example.tracker.models.prefs

interface Prefs {
    suspend fun getLoadedRanges(): List<Pair<Long, Long>>
    suspend fun putLoadedRanges(ranges: List<Pair<Long, Long>>)
    suspend fun clear()
}

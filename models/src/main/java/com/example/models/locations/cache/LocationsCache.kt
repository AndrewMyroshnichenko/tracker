package com.example.models.locations.cache

import com.example.models.locations.Location

interface LocationsCache {
    suspend fun getLocations(startDate: Long, endDate: Long): LocationsSet
    suspend fun putLocations(locations: List<Location>, startDate: Long, endDate: Long)
    suspend fun clear()
}

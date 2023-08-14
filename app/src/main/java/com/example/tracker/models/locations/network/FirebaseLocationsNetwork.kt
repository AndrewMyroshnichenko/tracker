package com.example.tracker.models.locations.network

import com.example.tracker.models.locations.Location
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseLocationsNetwork : LocationsNetwork {

    private val remoteDb: FirebaseFirestore = Firebase.firestore

    override suspend fun uploadLocation(location: Location) {
        remoteDb.collection(LOCATION_TABLE_NAME)
            .add(location)
            .await()
    }

    override suspend fun downloadLocations(ownerId: String): List<Location> {
        val list = mutableListOf<Location>()
        remoteDb.collection(LOCATION_TABLE_NAME)
            .whereEqualTo(COLUMN_OWNER_ID, ownerId)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val location = document.toObject(Location::class.java)
                    list.add(location)
                }
            }.await()
        return list
    }

    companion object {
        const val LOCATION_TABLE_NAME = "locations"
        const val COLUMN_OWNER_ID = "ownerId"
    }
}
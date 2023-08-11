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

    companion object {
        const val LOCATION_TABLE_NAME = "locations"
    }
}

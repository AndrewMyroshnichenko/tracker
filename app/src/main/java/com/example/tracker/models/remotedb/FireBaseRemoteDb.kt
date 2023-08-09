package com.example.tracker.models.remotedb

import com.example.tracker.data.locations.Location
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireBaseRemoteDb : RemoteDb {

    private val remoteDb: FirebaseFirestore = Firebase.firestore

    override fun addLocation(location: Location) {
        remoteDb.collection(LOCATION_TABLE_NAME).add(location)
    }

    companion object {
        const val LOCATION_TABLE_NAME = "locations"
    }

}
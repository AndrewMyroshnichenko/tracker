package com.example.tracker.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tracker.data.AppDatabase
import com.example.tracker.data.locations.RoomLocationsRepository
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.remotedb.FireBaseRemoteDb
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltWorker
class WorkerSendLocation @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    val mAuth: Auth
) : Worker(context, params) {

    private val repository = RoomLocationsRepository(AppDatabase.getDB(context).getMarkDao())

    @OptIn(DelicateCoroutinesApi::class)
    override fun doWork(): Result {
        val remoteDb = Firebase.firestore


        return try {
            GlobalScope.launch {
                Log.d("TAG", "Worker check local database")
                val localMarks = repository.getMarks(mAuth.getCurrentUserEmail())
                if (localMarks.isNotEmpty()){
                    Log.d("TAG", "Worker push local data to firestore")
                    localMarks.forEach { mark ->
                        remoteDb.collection(FireBaseRemoteDb.LOCATION_TABLE_NAME)
                            .add(mark)
                            .addOnSuccessListener {
                                GlobalScope.launch {
                                    repository.deleteMark(mark)
                                    Log.d("TAG", "Worker delete local mark")
                                }
                            }
                    }
                }

            }
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }
}
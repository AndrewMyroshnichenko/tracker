package com.example.tracker.bg.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tracker.models.locations.LocationsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltWorker
class UploadLocationsWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    val repository: LocationsRepository
) : Worker(context, params) {


    @OptIn(DelicateCoroutinesApi::class)
    override fun doWork(): Result {
        return try {
            GlobalScope.launch {

                Log.d("TAGG", "Worker check local database")
                val localMarks = repository.getMarks()

                if (localMarks.isNotEmpty()) {
                    Log.d("TAGG", "Worker push local data to firestore")

                    localMarks.forEach { mark ->
                        repository.insertLocation(mark)
                        Log.d("TAGG", "Worker delete local mark")
                    }
                }

            }
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }
}

package com.example.tracker.bg.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
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
    private val repository: LocationsRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("TAGG", "Worker check local database")
            val localMarks = repository.getLocations()

            if (localMarks.isNotEmpty()) {
                Log.d("TAGG", "Worker push local data to firestore")

                localMarks.forEach { location ->
                    repository.uploadLocation(location)
                    repository.deleteLocation(location)
                    Log.d("TAGG", "Worker delete local mark")
                }
            }
            Result.success()
        } catch (_: Exception) {
            Result.failure()
        }
    }
}

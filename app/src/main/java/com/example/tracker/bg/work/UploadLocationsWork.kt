package com.example.tracker.bg.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tracker.models.locations.LocationsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

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
                    try {
                        repository.saveLocation(location)
                        Log.d("TAGG", "Worker delete local mark")
                    } catch (e: Exception) {
                        Log.e("TAGG", "Failed to save location: ${e.message}")
                    }
                }
            }
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}

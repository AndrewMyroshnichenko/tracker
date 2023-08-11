package com.example.tracker.di

import android.content.Context
import com.example.tracker.bg.LocationController
import com.example.tracker.bg.LocationServiceController
import com.example.tracker.bg.work.UploadWorkScheduler
import com.example.tracker.bg.work.WorkScheduler
import com.example.tracker.models.AppDatabase
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.auth.FireBaseAuth
import com.example.tracker.models.bus.StatusManager
import com.example.tracker.models.bus.TrackerStatusManager
import com.example.tracker.models.gps.DefaultLocationSource
import com.example.tracker.models.gps.LocationSource
import com.example.tracker.models.locations.LocationsRepository
import com.example.tracker.models.locations.LocationsRepositoryImp
import com.example.tracker.models.locations.dao.LocationsDao
import com.example.tracker.models.locations.network.FirebaseLocationsNetwork
import com.example.tracker.models.locations.network.LocationsNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TrackerModule {

    @Provides
    @Singleton
    fun provideDefaultLocationSource(@ApplicationContext context: Context): LocationSource {
        return DefaultLocationSource(context)
    }

    @Provides
    @Singleton
    fun provideDefaultLocationModel(): StatusManager {
        return TrackerStatusManager()
    }

    @Provides
    @Singleton
    fun provideAuth(): Auth {
        return FireBaseAuth()
    }

    @Provides
    @Singleton
    fun provideRemoteDb(): LocationsNetwork {
        return FirebaseLocationsNetwork()
    }

    @Provides
    fun provideLocationServiceController(
        locationSource: LocationSource,
        model: StatusManager,
        locationRepository: LocationsRepository,
        uploadWorkScheduler: WorkScheduler
    ): LocationController {
        return LocationServiceController(
            location = locationSource,
            gpsStateCache = model,
            locationRepository = locationRepository,
            uploadWorkScheduler = uploadWorkScheduler
        )
    }

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDB(context)
    }

    @Provides
    @Singleton
    fun provideMarkDao(database: AppDatabase): LocationsDao {
        return database.getMarkDao()
    }

    @Provides
    fun provideLocationRepository(
        dao: LocationsDao,
        network: LocationsNetwork
    ): LocationsRepository {
        return LocationsRepositoryImp(dao, network)
    }

    @Provides
    fun provideWorkController(@ApplicationContext context: Context): WorkScheduler {
        return UploadWorkScheduler(context)
    }

}

package com.example

import android.content.Context
import com.example.data.locations.dao.MapLocationsDao
import com.example.data.locations.dao.TrackerLocationsDao
import com.example.models.LocationsRepository
import com.example.data.locations.LocationsRepositoryImp
import com.example.models.auth.Auth
import com.example.models.bg.LocationController
import com.example.models.bg.work.WorkScheduler
import com.example.models.bus.StatusManager
import com.example.models.gps.LocationSource
import com.example.models.locations.cache.LocationsCache
import com.example.models.locations.network.LocationsNetwork
import com.example.models.prefs.MapPrefs
import com.example.models_impl.bg.LocationServiceController
import com.example.models_impl.bg.work.UploadWorkScheduler
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
    fun provideAuth(): Auth = com.example.models_impl.auth.FireBaseAuth()

    @Provides
    @Singleton
    fun provideDefaultLocationSource(@ApplicationContext context: Context): LocationSource {
        return com.example.models_impl.gps.DefaultLocationSource(context)
    }

    @Provides
    @Singleton
    fun provideDefaultLocationModel(): StatusManager =
        com.example.models_impl.bus.TrackerStatusManager()

    @Provides
    @Singleton
    fun provideLocationsCache(prefs: MapPrefs): LocationsCache {
        return com.example.models_impl.locations.cache.LocationsCacheImpl(prefs)
    }

    @Provides
    fun provideRemoteDb(): LocationsNetwork =
        com.example.models_impl.locations.network.FirebaseLocationsNetwork()

    @Provides
    fun provideLocationServiceController(
        locationSource: LocationSource,
        model: StatusManager,
        locationRepository: LocationsRepository,
        uploadWorkScheduler: WorkScheduler,
        prefs: com.example.models.prefs.TrackerPrefs
    ): LocationController {
        return LocationServiceController(
            locationSource, model, locationRepository, uploadWorkScheduler, prefs
        )
    }

    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDB(context)
    }

    @Provides
    @Singleton
    fun provideLocationDao(database: AppDatabase): TrackerLocationsDao {
        return database.getLocationsDao()
    }

    @Provides
    @Singleton
    fun provideMapLocationDao(database: AppDatabase): MapLocationsDao {
        return database.getLoadedLocationsDao()
    }

    @Provides
    fun provideLocationRepository(
        trackerDao: TrackerLocationsDao,
        mapDao: MapLocationsDao,
        network: LocationsNetwork,
        cache: LocationsCache,
        auth: Auth
    ): LocationsRepository {
        return LocationsRepositoryImp(trackerDao, mapDao, network, auth, cache)
    }

    @Provides
    fun provideWorkController(@ApplicationContext context: Context): WorkScheduler {
        return UploadWorkScheduler(context)
    }

    @Provides
    @Singleton
    fun provideTrackerDataStorePrefs(@ApplicationContext context: Context): com.example.models.prefs.TrackerPrefs {
        return com.example.models_impl.prefs.TrackerDataStorePrefs(context)
    }

    @Provides
    @Singleton
    fun provideMapDataStorePrefs(@ApplicationContext context: Context): MapPrefs {
        return com.example.models_impl.prefs.MapDataStorePrefs(context)
    }

}

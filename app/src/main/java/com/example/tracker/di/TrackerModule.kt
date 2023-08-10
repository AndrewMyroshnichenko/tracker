package com.example.tracker.di

import android.content.Context
import com.example.tracker.bg.LocationController
import com.example.tracker.bg.LocationServiceController
import com.example.tracker.models.AppDatabase
import com.example.tracker.models.auth.AuthRepository
import com.example.tracker.models.auth.AuthRepositoryImpl
import com.example.tracker.models.auth.dao.UserDao
import com.example.tracker.models.auth.network.Auth
import com.example.tracker.models.auth.network.FireBaseAuth
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
        userRepository: AuthRepository,
        remoteDb: LocationsNetwork,
        authNetwork: Auth
    ): LocationController {
        return LocationServiceController(
            location = locationSource,
            gpsStateCache =  model,
            locationRepository = locationRepository,
            userRepository = userRepository,
            remoteDb = remoteDb,
            authNetwork = authNetwork
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
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.getUserDao()
    }

    @Provides
    fun provideLocationRepository(dao: LocationsDao): LocationsRepository {
        return LocationsRepositoryImp(dao)
    }

    @Provides
    fun provideUserRepository(dao: UserDao): AuthRepository {
        return AuthRepositoryImpl(dao)
    }

}

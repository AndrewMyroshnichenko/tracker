package com.example.tracker.di

import android.content.Context
import android.location.LocationManager
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.auth.FbAuth
import com.example.tracker.models.gps.DefaultLocationSource
import com.example.tracker.models.gps.StatusManager
import com.example.tracker.models.gps.TrackerStatusManager
import com.example.tracker.models.gps.LocationServiceController
import com.example.tracker.models.gps.LocationServiceInterface
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TrackerModule {

    @Provides
    @Singleton
    fun provideDefaultLocationSource(
        @ApplicationContext context: Context,
        model: StatusManager, locationManager: LocationManager
    ): DefaultLocationSource {
        return DefaultLocationSource(context, model, locationManager)
    }

    @Provides
    @Singleton
    fun provideDefaultLocationModel(): StatusManager {
        return TrackerStatusManager(MutableStateFlow(false), MutableStateFlow(true))
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuth(mAuth: FirebaseAuth): Auth {
        return FbAuth(mAuth)
    }


    @Provides
    fun provideLocationServiceController(
        locationSource: DefaultLocationSource,
        model: StatusManager
    ): LocationServiceInterface {
        return LocationServiceController(locationSource, model)
    }

    @Provides
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

}
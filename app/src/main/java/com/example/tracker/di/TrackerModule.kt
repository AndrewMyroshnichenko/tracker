package com.example.tracker.di

import android.content.Context
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.auth.FbAuth
import com.example.tracker.models.gps.DefaultLocationSource
import com.example.tracker.models.gps.LocationInterface
import com.example.tracker.models.gps.LocationModel
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
        model: LocationInterface
    ): DefaultLocationSource {
        return DefaultLocationSource(context, model)
    }

    @Provides
    @Singleton
    fun provideDefaultLocationModel(): LocationInterface {
        return LocationModel(MutableStateFlow(false), MutableStateFlow(true))
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
        model: LocationInterface
    ): LocationServiceInterface {
        return LocationServiceController(locationSource, model)
    }

}
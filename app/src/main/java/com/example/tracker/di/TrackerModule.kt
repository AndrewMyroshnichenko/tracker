package com.example.tracker.di

import android.content.Context
import com.example.tracker.bg.LocationServiceController
import com.example.tracker.bg.LocationController
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.auth.FireBaseAuth
import com.example.tracker.models.bus.StatusManager
import com.example.tracker.models.bus.TrackerStatusManager
import com.example.tracker.models.gps.DefaultLocationSource
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
    fun provideDefaultLocationSource(@ApplicationContext context: Context): DefaultLocationSource {
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
    fun provideLocationServiceController(
        locationSource: DefaultLocationSource, model: StatusManager
    ): LocationController {
        return LocationServiceController(locationSource, model)
    }
}

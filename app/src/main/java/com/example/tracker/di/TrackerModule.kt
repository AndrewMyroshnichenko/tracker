package com.example.tracker.di

import android.content.Context
import com.example.tracker.bg.LocationServiceController
import com.example.tracker.bg.LocationServiceInterface
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
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TrackerModule {

    @Provides
    @Singleton
    fun provideDefaultLocationSource(
        @ApplicationContext context: Context, model: StatusManager
    ): DefaultLocationSource {
        return DefaultLocationSource(context, model)
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
    ): LocationServiceInterface {
        return LocationServiceController(locationSource, model)
    }
}

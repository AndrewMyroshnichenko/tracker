package com.example.tracker.di

import android.content.Context
import com.example.tracker.models.auth.Auth
import com.example.tracker.models.auth.FbAuth
import com.example.tracker.models.gps.DefaultLocationSource
import com.google.firebase.auth.FirebaseAuth
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
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuth(): Auth {
        return FbAuth()
    }

    @Provides
    @Singleton
    fun provideDefaultLocationSource(@ApplicationContext context: Context): DefaultLocationSource {
        return DefaultLocationSource(context)
    }

}
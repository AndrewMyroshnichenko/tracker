package com.example.tracker.models.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersHolder {

    fun getMain(): CoroutineDispatcher

    fun getIO(): CoroutineDispatcher
}

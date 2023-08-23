package com.example.models.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersHolder {

    fun getMain(): CoroutineDispatcher

    fun getIO(): CoroutineDispatcher
}

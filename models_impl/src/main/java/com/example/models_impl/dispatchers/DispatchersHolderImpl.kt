package com.example.models_impl.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatchersHolderImpl : com.example.models.dispatchers.DispatchersHolder {

    override fun getMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun getIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}

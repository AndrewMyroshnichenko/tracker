package com.example.tracker.models.auth.network

interface Auth {

    suspend fun signIn(userEmail: String, userPassword: String)

    suspend fun signUp(userEmail: String, userPassword: String)

    suspend fun forgotPassword(userEmail: String)

    fun signOut()

    fun isSignedIn(): Boolean

    fun getCurrentUserEmail(): String
}

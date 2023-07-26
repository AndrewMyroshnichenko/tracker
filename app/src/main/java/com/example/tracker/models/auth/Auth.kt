package com.example.tracker.models.auth

interface Auth {

    suspend fun signIn(userEmail: String, userPassword: String): Pair<Boolean, Int?>

    suspend fun signUp(userEmail: String, userPassword: String): Pair<Boolean, Int?>

    suspend fun forgotPassword(userEmail: String): Pair<Boolean, Int?>

    fun signOut()

    fun isSignedIn(): Boolean
}

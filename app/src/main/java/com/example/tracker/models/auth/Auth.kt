package com.example.tracker.models.auth

interface Auth {

    fun signIn(userEmail: String, userPassword: String, callback: (Boolean, Int?) -> Unit)

    fun signUp(userEmail: String, userPassword: String, callback: (Boolean, Int?) -> Unit)

    fun forgotPassword(userEmail: String, callback: (Boolean, Int?) -> Unit)

    fun signOut()

    fun isSignedIn(): Boolean
}
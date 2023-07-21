package com.example.tracker.models

interface FirebaseInterface {

    fun signIn(userEmail: String, userPassword: String, callback: (Boolean, String?) -> Unit)

    fun signUp(userEmail: String, userPassword: String, callback: (Boolean, String?) -> Unit)

    fun forgotPassword(userEmail: String, callback: (Boolean, String?) -> Unit)

    fun signOut()

    fun isSignedIn(): Boolean
}
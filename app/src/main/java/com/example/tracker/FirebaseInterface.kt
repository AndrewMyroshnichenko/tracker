package com.example.tracker

import com.example.tracker.ui.login.LoginState

interface FirebaseInterface {

    fun signIn(userEmail: String, userPassword: String): LoginState?

    fun signUp(userEmail: String, userPassword: String): LoginState?

    fun forgotPassword(userEmail: String): LoginState?

    fun signOut(): LoginState

    fun isSignedIn(): Boolean
}
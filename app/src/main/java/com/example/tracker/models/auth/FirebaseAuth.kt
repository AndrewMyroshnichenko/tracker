package com.example.tracker.models.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuth : Auth {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signIn(userEmail: String, userPassword: String) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword).await()
    }

    override suspend fun signUp(userEmail: String, userPassword: String) {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).await()
    }

    override suspend fun forgotPassword(userEmail: String) {
        mAuth.sendPasswordResetEmail(userEmail).await()
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun isSignedIn(): Boolean = mAuth.currentUser != null
}

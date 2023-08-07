package com.example.tracker.models.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FbAuth @Inject constructor(val mAuth: FirebaseAuth) : Auth {

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

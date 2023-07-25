package com.example.tracker.models.auth

import com.example.tracker.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuth : Auth {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signIn(userEmail: String, userPassword: String): Pair<Boolean, Int?> {
        return try {
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).await()
            Pair(true, null)
        } catch (e: Exception) {
            Pair(false, R.string.login_failed)
        }
    }

    override suspend fun signUp(userEmail: String, userPassword: String): Pair<Boolean, Int?> {
        return try {
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword).await()
            Pair(true, R.string.registration_completed)
        } catch (e: Exception) {
            Pair(false, R.string.registration_failed)
        }
    }

    override suspend fun forgotPassword(userEmail: String): Pair<Boolean, Int?> {
        return try {
            val result = mAuth.fetchSignInMethodsForEmail(userEmail).await()
            if (result.signInMethods.isNullOrEmpty()) {
                Pair(false, R.string.this_email_doesnt_exist)
            } else {
                mAuth.sendPasswordResetEmail(userEmail).await()
                Pair(true, R.string.check_your_email)
            }
        } catch (e: Exception) {
            Pair(false, R.string.something_went_wrong_password_wasnt_reset)
        }
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun isSignedIn(): Boolean = mAuth.currentUser != null
}

package com.example.tracker.models

import com.example.tracker.R
import com.google.firebase.auth.FirebaseAuth

class FirebaseManager : FirebaseInterface {

    //TODO: When I add DI, I'll move this to the constructor
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun signIn(
        userEmail: String,
        userPassword: String,
        callback: (Boolean, Int?) -> Unit
    ) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, R.string.login_failed)
                }
            }
    }

    override fun signUp(
        userEmail: String,
        userPassword: String,
        callback: (Boolean, Int?) -> Unit
    ) {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, R.string.registration_completed)
                } else {
                    callback(false, R.string.registration_failed)
                }
            }
    }

    override fun forgotPassword(userEmail: String, callback: (Boolean, Int?) -> Unit) {
        mAuth.fetchSignInMethodsForEmail(userEmail)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.signInMethods.isNullOrEmpty()) {
                        callback(false, R.string.this_email_doesnt_exist)
                    } else {
                        mAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {
                                    callback(true, R.string.check_your_email)
                                } else {
                                    callback(false, R.string.something_went_wrong_password_wasnt_reset)
                                }
                            }
                    }
                } else {
                    callback(false, R.string.cant_send_request)
                }
            }
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun isSignedIn(): Boolean {
        return mAuth.currentUser != null
    }

}
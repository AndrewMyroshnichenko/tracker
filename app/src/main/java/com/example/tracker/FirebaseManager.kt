package com.example.tracker

import com.example.tracker.ui.login.LoginState
import com.google.firebase.auth.FirebaseAuth

class FirebaseManager : FirebaseInterface {

    //When I add DI, I'll move this to the constructor
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun signIn(
        userEmail: String,
        userPassword: String,
        callback: (Boolean, String?) -> Unit
    ) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, "Login failed!")
                }
            }
    }

    override fun signUp(
        userEmail: String,
        userPassword: String,
        callback: (Boolean, String?) -> Unit
    ) {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, "Registration failed!")
                }
            }
    }

    override fun forgotPassword(userEmail: String, callback: (Boolean, String?) -> Unit) {
        mAuth.fetchSignInMethodsForEmail(userEmail)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.signInMethods.isNullOrEmpty()) {
                        callback(false, "This email doesn't exist")
                    } else {
                        mAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {
                                    callback(true, null)
                                } else {
                                    callback(false, "Something went wrong, password wasn't reset")
                                }
                            }
                    }
                } else {
                    callback(false, "Can't send request")
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
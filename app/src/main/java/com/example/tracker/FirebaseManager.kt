package com.example.tracker

import android.util.Log
import com.example.tracker.ui.login.LoginState
import com.google.firebase.auth.FirebaseAuth


class FirebaseManager : FirebaseInterface {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    //I know why it doesn't work, but I haven't found a solution yet
    override fun signIn(userEmail: String, userPassword: String): LoginState? {

        var state: LoginState? = null

        Log.d("MIROSH", "INIT S: ${state.toString()}")

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                state = if (task.isSuccessful) {
                    LoginState.SuccessSignIn(userEmail)
                } else {
                    LoginState.ShowError("Login failed!")
                }
            }
        Log.d("MIROSH", "S: ${state.toString()}")
        return state

    }
    //I know why it doesn't work, but I haven't found a solution yet
    override fun signUp(userEmail: String, userPassword: String): LoginState? {

        var state: LoginState? = null

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                state = if (it.isSuccessful) {
                    LoginState.SuccessSignUp
                } else {
                    LoginState.ShowError("Registration failed!")
                }
            }

        return state

    }
    //I know why it doesn't work, but I haven't found a solution yet
    override fun forgotPassword(userEmail: String): LoginState? {

        var state: LoginState? = null

        mAuth.fetchSignInMethodsForEmail(userEmail)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.signInMethods.isNullOrEmpty()) {
                        state = LoginState.ShowError("This email doesn't exist")
                    } else {
                        mAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener { sendTask ->
                                state = if (sendTask.isSuccessful) {
                                    LoginState.SuccessResetPassword
                                } else {
                                    LoginState.ShowError("Something went wrong, password wasn't reset")
                                }
                            }
                    }
                } else {
                    state = LoginState.ShowError("Can't send request")
                }
            }

        return state

    }
    //I know why it doesn't work, but I haven't found a solution yet
    override fun signOut(): LoginState {
        mAuth.signOut()
        return LoginState.SuccessSignOutState
    }
    //I know why it doesn't work, but I haven't found a solution yet
    override fun isSignedIn(): Boolean {
        return mAuth.currentUser != null
    }

}
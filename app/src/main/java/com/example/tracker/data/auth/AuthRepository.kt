package com.example.tracker.data.auth

interface AuthRepository {
    suspend fun updateCurrentUser(user: User)

    suspend fun getCurrentUser(): User?
}
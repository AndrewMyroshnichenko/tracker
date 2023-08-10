package com.example.tracker.models.auth

interface AuthRepository {
    suspend fun updateCurrentUser(user: User)

    suspend fun getCurrentUser(): User?
}

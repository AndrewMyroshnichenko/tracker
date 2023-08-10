package com.example.tracker.models.auth

data class User(
    val id: Int = 0,
    val userEmail: String = "",
    val isTrackingOn: String = "",
)
package com.example.tracker.data.auth

import com.example.tracker.data.auth.dao.UserDao
import com.example.tracker.data.auth.dao.UserDbEntity

class RoomAuthRepository (val dao: UserDao) : AuthRepository {

    override suspend fun updateCurrentUser(user: User) =
        dao.upsertUser(UserDbEntity.toUserDbEntity(user))

    override suspend fun getCurrentUser(): User? = dao.getCurrentUser()?.toUser()
}
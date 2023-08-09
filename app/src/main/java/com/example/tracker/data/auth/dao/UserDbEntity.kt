package com.example.tracker.data.auth.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tracker.data.auth.User

@Entity(UserDbEntity.USER_TABLE_NAME)
class UserDbEntity(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo("email") val userEmail: String,
    @ColumnInfo("is_tracking_on") val isTrackingOn: String
) {

    fun toUser(): User = User(id = id, userEmail = userEmail, isTrackingOn = isTrackingOn)

    companion object {

        const val USER_TABLE_NAME = "users"

        fun toUserDbEntity(user: User) = UserDbEntity(
            id = user.id, userEmail = user.userEmail, isTrackingOn = user.isTrackingOn
        )
    }
}
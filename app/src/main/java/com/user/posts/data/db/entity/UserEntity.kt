package com.user.posts.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    val name: String,
    val url: String,
    val thumbnailUrl: String,
    val postsCount: Int = 0
)
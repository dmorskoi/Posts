package com.user.posts.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val albumId: Int,
    val userId: Int,
    val name: String,
    val url: String,
    val thumbnailUrl: String
)
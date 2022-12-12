package com.user.posts.feature.users

data class UsersState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val users: List<UiUser> = emptyList()
)

data class UiUser(
    val userId: Int,
    val userName: String = "",
    val userLogoUrl: String = "",
    val postsCount: Int
)
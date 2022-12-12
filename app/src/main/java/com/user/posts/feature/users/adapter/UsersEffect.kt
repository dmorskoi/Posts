package com.user.posts.feature.users.adapter

import androidx.navigation.NavDirections

data class UserUiEffect(
    val navAction: NavDirections? = null,
    val isError: Boolean = false
)
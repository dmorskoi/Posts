package com.user.posts.feature.splash

import androidx.navigation.NavDirections

data class SplashUiEffect(
    val navAction: NavDirections? = null,
    val isError: Boolean = false
)
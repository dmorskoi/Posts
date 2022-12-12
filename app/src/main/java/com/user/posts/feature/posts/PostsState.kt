package com.user.posts.feature.posts

data class PostsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val userLogoUrl: String = "",
    val posts: List<UiPost> = emptyList()
)

data class UiPost(
    val postId: Int,
    val title: String = "",
    val userLogoUrl: String = "",
    val body: String = ""
)
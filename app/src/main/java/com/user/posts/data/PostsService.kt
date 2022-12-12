package com.user.posts.data

import com.user.posts.data.dto.Post
import retrofit2.http.GET

interface PostsService {

    @GET("SharminSirajudeen/test_resources/posts")
    suspend fun getPosts(): List<Post>
}
package com.user.posts.data

import com.user.posts.data.dto.User
import retrofit2.http.GET

interface UsersService {

    @GET("SharminSirajudeen/test_resources/users")
    suspend fun getusers(): List<User>
}
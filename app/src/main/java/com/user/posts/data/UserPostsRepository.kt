package com.user.posts.data

import com.user.posts.data.db.dao.PostDao
import com.user.posts.data.db.dao.UserDao
import com.user.posts.data.db.entity.PostEntity
import com.user.posts.data.db.entity.UserEntity
import com.user.posts.data.dto.Post
import com.user.posts.data.dto.User
import com.user.posts.feature.posts.UiPost
import com.user.posts.feature.users.UiUser
import javax.inject.Inject

class UserPostsRepository @Inject constructor(
    private val userDao: UserDao,
    private val postsDao: PostDao,
    private val usersService: UsersService,
    private val postsService: PostsService
) {

    suspend fun fetchData() {
        val users = usersService.getusers()
        val posts = postsService.getPosts()
        insertUsers(users.toUserEntity())
        insertPosts(posts.toPostEntity())
    }

    suspend fun getUsers(): Result<List<UiUser>> = runCatching {
        userDao.getUsers().toUiUsers()
    }

    suspend fun getPostsByUserId(userId: Int): Result<List<UiPost>> = runCatching {
        postsDao.getPostsByUserId(userId).toUiPosts()
    }

    private suspend fun insertUsers(users: List<UserEntity>) {
        userDao.insertAll(users)
    }

    private suspend fun insertPosts(posts: List<PostEntity>) {
        postsDao.insertAll(posts)
    }
}

fun List<User>.toUserEntity() =
    map {
        UserEntity(
            userId = it.userId,
            name = it.name,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }

fun List<UserEntity>.toUiUsers() =
    map {
        UiUser(
            userId = it.userId,
            userName = it.name,
            userLogoUrl = it.url,
            postsCount = it.postsCount
        )
    }

fun List<Post>.toPostEntity(): List<PostEntity> =
    map {
        PostEntity(
            id = it.id,
            userId = it.userId,
            title = it.title,
            body = it.body
        )
    }

fun List<PostEntity>.toUiPosts() =
    map {
        UiPost(
            postId = it.id,
            userLogoUrl = it.url,
            title = it.title,
            body = it.body
        )
    }
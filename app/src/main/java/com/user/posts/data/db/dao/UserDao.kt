package com.user.posts.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.user.posts.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT  userId, name, url, thumbnailUrl, (SELECT COUNT(*) FROM posts where posts.userId = users.userId ) as postsCount  FROM users")
    suspend fun getUsers(): List<UserEntity>
}
package com.user.posts.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.user.posts.data.db.entity.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("SELECT posts.id, posts.userId, users.url as url, posts.title, posts.body FROM posts JOIN users on posts.userId = users.userId WHERE users.userId =:userId")
    suspend fun getPostsByUserId(userId: Int): List<PostEntity>
}
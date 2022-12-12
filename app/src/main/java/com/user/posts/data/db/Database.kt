package com.user.posts.data.db

import androidx.room.RoomDatabase
import com.user.posts.data.db.dao.PostDao
import com.user.posts.data.db.dao.UserDao
import com.user.posts.data.db.entity.PostEntity
import com.user.posts.data.db.entity.UserEntity
import androidx.room.Database as Db

@Db(
    entities = [
        UserEntity::class,
        PostEntity::class,
    ],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getPostDao(): PostDao
}
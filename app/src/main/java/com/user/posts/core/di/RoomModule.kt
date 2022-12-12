package com.user.posts.core.di

import android.content.Context
import androidx.room.Room
import com.user.posts.data.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): Database {
        return Room
            .databaseBuilder(
                appContext,
                Database::class.java,
                DATABASE_NAME
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: Database) = database.getUserDao()

    @Singleton
    @Provides
    fun providePostDao(database: Database) = database.getPostDao()

    private companion object {
        private const val DATABASE_NAME = "user_posts"
    }
}
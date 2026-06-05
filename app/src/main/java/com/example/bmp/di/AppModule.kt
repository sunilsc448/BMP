package com.example.bmp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bmp.data.local.AppDatabase
import com.example.bmp.data.local.ArticleDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context = context, name = "articles_db", klass = AppDatabase::class.java).build()
    
    @Provides
    @Singleton
    fun provideArticleDao(db: AppDatabase): ArticleDao = db.articleDao()
}
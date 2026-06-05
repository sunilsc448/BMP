package com.example.bmp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)
    
    @Query("Select * from articles")
    fun getArticles(): Flow<List<ArticleEntity>>
    
    @Query("Select * from articles where isBookmarked = 1")
    fun getBookmarkedArticles(): Flow<List<ArticleEntity>>
    
    @Query("UPDATE articles SET isBookmarked = :isBookmarked where id = :id")
    suspend fun toggleBookmark(id: String, isBookmarked: Boolean)
    
    @Query("UPDATE articles SET note = :note where id = :id")
    suspend fun updateNote(id: String, note: String)
}
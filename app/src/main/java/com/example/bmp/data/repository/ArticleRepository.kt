package com.example.bmp.data.repository

import com.example.bmp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(): Flow<List<Article>>
    fun getBookmarkedArticles(): Flow<List<Article>>
    suspend fun toggleBookmark(id: String, isBookmarked: Boolean)
    suspend fun updateNote(id: String, note: String)
    suspend fun insertArticles(articles: List<Article>)
    suspend fun getArticlesCount(): Int
    suspend fun removeBookmarks(articleIds: Set<String>)
}
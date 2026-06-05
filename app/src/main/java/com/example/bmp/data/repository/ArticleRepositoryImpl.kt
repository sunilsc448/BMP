package com.example.bmp.data.repository

import com.example.bmp.data.local.ArticleDao
import com.example.bmp.domain.model.Article
import com.example.bmp.domain.model.toArticle
import com.example.bmp.domain.model.toArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor (private val articleDao: ArticleDao): ArticleRepository {
    
    override fun getArticles(): Flow<List<Article>> {
        return articleDao.getArticles().map {articles ->  articles.map { it.toArticle() } }
    }
    
    override fun getBookmarkedArticles(): Flow<List<Article>> {
        return articleDao.getBookmarkedArticles().map { articles -> articles.map { it.toArticle() } }
    }
    
    override suspend fun toggleBookmark(id: String, isBookmarked: Boolean) {
        articleDao.toggleBookmark(id = id, isBookmarked = isBookmarked)
    }
    
    override suspend fun updateNote(id: String, note: String) {
        articleDao.updateNote(id = id, note = note)
    }
    
    override suspend fun insertArticles(articles: List<Article>) {
        articleDao.insertArticles(articles.map {it.toArticleEntity() })
    }
}
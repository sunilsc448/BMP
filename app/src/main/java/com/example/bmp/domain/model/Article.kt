package com.example.bmp.domain.model

import com.example.bmp.data.local.ArticleEntity

data class Article(
        val id: String,
        val title: String,
        val summary: String,
        val imageUrl: String,
        val isBookmarked: Boolean = false,
        val note:String = ""
)

fun ArticleEntity.toArticle(): Article = Article(
        id = id,
        title = title,
        summary = summary,
        imageUrl = imageUrl,
        isBookmarked = isBookmarked,
        note = note
)

fun Article.toArticleEntity(): ArticleEntity = ArticleEntity(
        id = id,
        title = title,
        summary = summary,
        imageUrl = imageUrl,
        isBookmarked = isBookmarked,
        note = note
)
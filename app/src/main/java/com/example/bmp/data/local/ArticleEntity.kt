package com.example.bmp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
        @PrimaryKey
        val id: String,
        val title: String,
        val summary: String,
        val imageUrl: String,
        val isBookmarked: Boolean = false,
        val note:String = ""
)
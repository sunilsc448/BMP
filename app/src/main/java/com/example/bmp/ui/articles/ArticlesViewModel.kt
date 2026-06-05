package com.example.bmp.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bmp.data.repository.ArticleRepository
import com.example.bmp.data.sampleSeed.SampleData
import com.example.bmp.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(private val articleRepository: ArticleRepository): ViewModel() {
    val articles: StateFlow<List<Article>> = articleRepository.getArticles().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
    )
    
    fun toggleBookmark(id:String, isBookmarked: Boolean){
        viewModelScope.launch {
            articleRepository.toggleBookmark(id, isBookmarked)
        }
    }
    
    fun seedArticles(){
        viewModelScope.launch {
            articleRepository.insertArticles(SampleData.articles)
        }
    }
}
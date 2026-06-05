package com.example.bmp.ui.bookmarks

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bmp.data.repository.ArticleRepository
import com.example.bmp.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class BookmarksViewModel @Inject constructor(private val articleRepository: ArticleRepository): ViewModel() {
    val bookmarks: StateFlow<List<Article>> = articleRepository.getBookmarkedArticles()
            .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList(),
            )
    
    fun updateNote(id:String, note: String){
        viewModelScope.launch {
            articleRepository.updateNote(id, note)
        }
    }
    
    fun toggleBookmark(id:String){
        viewModelScope.launch {
            articleRepository.toggleBookmark(id, false)
        }
    }
}
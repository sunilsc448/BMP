package com.example.bmp.ui.bookmarks

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bmp.data.repository.ArticleRepository
import com.example.bmp.domain.model.Article
import com.example.bmp.ui.navigation.Screen.Bookmarks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    
    private val _noteDialogTargetId = MutableStateFlow<String?>(null)
    val noteDialogTargetId: StateFlow<String?> = _noteDialogTargetId
    
    private val _selectedIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedIds: StateFlow<Set<String>> = _selectedIds
    
    fun openDialog(id:String){
        _noteDialogTargetId.value = id
    }
    
    fun dismissDialog(){
        _noteDialogTargetId.value = null
    }
    
    fun saveNote(id:String, note: String){
        viewModelScope.launch {
            articleRepository.updateNote(id, note)
        }
        dismissDialog()
    }
    
    fun toggleSelection(id: String) {
        _selectedIds.update { current ->
            if (id in current)
                current - id
            else
                current + id
        }
    }
    
    fun toggleBookmark(id:String){
        viewModelScope.launch {
            articleRepository.toggleBookmark(id, false)
        }
    }
    
    fun deleteSelectedBookmarks(){
        viewModelScope.launch {
            articleRepository.removeBookmarks(_selectedIds.value)
            clearSelectedBookmarks()
        }
    }
    
    fun clearSelectedBookmarks(){
        _selectedIds.value = emptySet()
    }
}
package com.example.bmp.ui.bookmarks

import com.example.bmp.data.repository.ArticleRepository
import com.example.bmp.util.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarksViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private val repository: ArticleRepository = mockk(relaxed = true)
    
    private lateinit var viewModel: BookmarksViewModel
    
    @Before
    fun setUp() {
        every {
            repository.getBookmarkedArticles()
        }returns flowOf(emptyList())
        viewModel = BookmarksViewModel(repository)
    }
    
    @Test
    fun `toggleSelection adds id to selectionIds`(){
        viewModel.toggleSelection("1")
        assert(viewModel.selectedIds.value.contains("1"))
    }
    
    @Test
    fun `toggleSelection removes id if already selected`(){
        viewModel.toggleSelection("1")
        viewModel.toggleSelection("1")
        assert(viewModel.selectedIds.value.isEmpty())
    }
    
    @Test
    fun `clearSelection empties selectedIds` (){
        viewModel.toggleSelection("1")
        viewModel.toggleSelection("2")
        viewModel.clearSelectedBookmarks()
        assert(viewModel.selectedIds.value.isEmpty())
    }
    
    @Test
    fun `openDialog sets noteDialogTargetId`() {
        viewModel.openDialog("article_1")
        assert(viewModel.noteDialogTargetId.value == "article_1")
    }
    
    @Test
    fun `dismissDialog clears noteDialogTargetId`() {
        viewModel.openDialog("article_1")
        viewModel.dismissDialog()
        assert(viewModel.noteDialogTargetId.value == null)
    }
    
    @Test
    fun `deleteSelectedBookmarks calls repostory and clears all selected bookmarks`() = runTest {
        viewModel.toggleSelection("1")
        viewModel.toggleSelection("2")
        viewModel.deleteSelectedBookmarks()
        coVerify { repository.removeBookmarks(setOf("1", "2")) }
        assert(viewModel.selectedIds.value.isEmpty())
    }
    
    @Test
    fun `toggleBookmark calls repository with false`() = runTest {
        viewModel.toggleBookmark("article_1")
        coVerify { repository.toggleBookmark("article_1", false) }
    }
    
    @Test
    fun `bookmarks StateFlow initial value is empty`() {
        // stateIn() inside ViewModel uses viewModelScope (Dispatchers.Main)
        assert(viewModel.bookmarks.value.isEmpty())
    }
}
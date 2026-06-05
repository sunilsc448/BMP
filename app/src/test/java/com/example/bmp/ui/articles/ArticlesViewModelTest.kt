package com.example.bmp.ui.articles

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
class ArticlesViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private val repository: ArticleRepository = mockk(relaxed = true)
    
    private lateinit var viewModel: ArticlesViewModel
    
    @Before
    fun setUp() {
        every {
            repository.getArticles()
        } returns flowOf(emptyList())
        viewModel = ArticlesViewModel(repository)
    }
    
    @Test
    fun `openDialog sets noteDialogTargetId`(){
        viewModel.openDialog("article_1")
        assert(viewModel.noteDialogTargetId.value == "article_1")
    }
    
    @Test
    fun `dismissDialog makes noteDialogTargetId null`(){
        viewModel.dismissDialog()
        assert(viewModel.noteDialogTargetId.value == null)
    }
    
    @Test
    fun `saveNote calls repository and dismisses dialog'`() = runTest {
        viewModel.openDialog("article_1")
        viewModel.saveNote("article_1", "My note")
        coVerify {repository.updateNote("article_1", "My note")}
        assert(viewModel.noteDialogTargetId.value == null)
    }
    
    @Test
    fun `toggleBookmark call repository to toggle the bookmark`() = runTest {
        viewModel.toggleBookmark("article_1", true)
        coVerify { repository.toggleBookmark("article_1", true) }
    }
    
    @Test
    fun `seedArticles calls repository insertArticles`() = runTest {
        viewModel.seedArticles()
        coVerify { repository.insertArticles(any()) }
    }
}
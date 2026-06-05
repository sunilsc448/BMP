package com.example.bmp.ui.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bmp.domain.model.Article
import com.example.bmp.ui.navigation.Screen.Bookmarks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(navController: NavHostController){
    val articlesViewModel: ArticlesViewModel = hiltViewModel()
    val articles by articlesViewModel.articles.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        articlesViewModel.seedArticles()
    }
    
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Articles") },
                        actions = {
                            IconButton(onClick = {
                                navController.navigate(Bookmarks.route)
                            }) {
                                Icon(Icons.Default.Bookmarks, contentDescription = "Bookmarks")
                            }
                        })
            }
    ){ paddingValues ->
        LazyColumn(
                contentPadding = paddingValues,
                state = rememberLazyListState()
        ) {
            items(articles, key = {it.id}){ article ->
                ArticleCard(article, {
                    articlesViewModel.toggleBookmark(article.id, !article.isBookmarked)
                })
            }
        }
    }
}

@Composable
fun ArticleCard(article: Article, onBookMarkClicked:() -> Unit){
    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column{
            AsyncImage(model = article.imageUrl,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(180.dp).fillMaxWidth())
            Row(modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text(article.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(article.summary, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                }
                IconButton(onClick = onBookMarkClicked) {
                    Icon(imageVector = if(article.isBookmarked){
                        Icons.Filled.Bookmark
                    }else{
                        Icons.Outlined.BookmarkBorder
                    }, contentDescription = if(article.isBookmarked){
                        "Remove bookmark"
                    }else{
                        "Add bookmark"
                    })
                }
            }
        }
    }
}
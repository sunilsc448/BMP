package com.example.bmp.ui.bookmarks

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(navController: NavHostController){
    val bookmarksViewModel: BookmarksViewModel = hiltViewModel()
    val bookmarks by bookmarksViewModel.bookmarks.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Bookmarks")
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })
    }) { paddingValues ->
            LazyColumn(contentPadding = paddingValues,
                    state = rememberLazyListState()) {
                items(bookmarks, key = {it.id}){bookmark ->
                    BookmarkCard(bookmark, {
                        bookmarksViewModel.toggleBookmark(bookmark.id)
                    })
                }
            }
    }
}

@Composable
fun BookmarkCard(bookmark: Article, onBookMarkClicked:() -> Unit){
    ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp) ) {
        Column{
            AsyncImage(model = bookmark.imageUrl,
                    contentDescription = bookmark.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(180.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(16.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(bookmark.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(bookmark.summary, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                }
                IconButton(onClick = onBookMarkClicked) {
                    Icon( Icons.Filled.Bookmark, contentDescription = "Remove bookmark")
                }
            }
        }
    }
}
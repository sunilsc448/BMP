package com.example.bmp.ui.bookmarks

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.outlined.EditNote
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.bmp.domain.model.Article
import com.example.bmp.ui.notes.NoteDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(navController: NavHostController){
    val bookmarksViewModel: BookmarksViewModel = hiltViewModel()
    val bookmarks by bookmarksViewModel.bookmarks.collectAsStateWithLifecycle()
    
    val selectedIds by bookmarksViewModel.selectedIds.collectAsStateWithLifecycle()
    
    BackHandler(enabled = selectedIds.isNotEmpty()) {
        bookmarksViewModel.clearSelectedBookmarks()
    }
    
    val noteDialogTargetId by bookmarksViewModel.noteDialogTargetId.collectAsStateWithLifecycle()
    noteDialogTargetId?.let { targetId ->
        val currentNote = bookmarks.find { it.id == targetId }?.note ?: ""
        NoteDialog(currentNote = currentNote, onDismiss = {
            bookmarksViewModel.dismissDialog()
        }, onSave = { updatedNote ->
            bookmarksViewModel.saveNote(id = targetId, note = updatedNote)
        })
    }
    
    Scaffold(topBar = {
        if(selectedIds.isNotEmpty()){
            TopAppBar(title = {Text("${selectedIds.size} selected")},
                    actions = {
                        IconButton(onClick = {
                            bookmarksViewModel.clearSelectedBookmarks()
                        }) {
                            Icon(imageVector = Icons.Default.Cancel, contentDescription = "Cancel")
                        }
                        IconButton(onClick = {
                            bookmarksViewModel.deleteSelectedBookmarks()
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                        }
                    })
        }else {
            TopAppBar(title = {
                Text("Bookmarks")
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
        }
    }) { paddingValues ->
            LazyColumn(contentPadding = paddingValues,
                    state = rememberLazyListState()) {
                items(bookmarks, key = {it.id}){bookmark ->
                    BookmarkCard(bookmark,
                            isSelected = bookmark.id in selectedIds,
                            isInSelectionMode = selectedIds.isNotEmpty(),
                            onBookMarkClicked = {
                        bookmarksViewModel.toggleBookmark(bookmark.id)
                    }, onNoteClick = {
                        bookmarksViewModel.openDialog(bookmark.id)
                    }, onLongClick = {
                        bookmarksViewModel.toggleSelection(bookmark.id)
                    })
                }
            }
    }
}

@Composable
fun BookmarkCard(bookmark: Article,
                 isSelected: Boolean,
                 isInSelectionMode:Boolean,
                 onBookMarkClicked:() -> Unit,
                 onNoteClick:() -> Unit,
                 onLongClick:() -> Unit){
    ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                    onClick = {if(isInSelectionMode) onLongClick()},
                    onLongClick = onLongClick
            )) {
        Column{
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(model = bookmark.imageUrl,
                        contentDescription = bookmark.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(180.dp))
                if (isSelected) {
                    Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(16.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(bookmark.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(bookmark.summary, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                    Spacer(Modifier.height(8.dp))
                    if (bookmark.note.isNotBlank()) {
                        Text(bookmark.note, style = MaterialTheme.typography.bodySmall,
                                maxLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                }
                IconButton(onClick = onBookMarkClicked) {
                    Icon( Icons.Filled.Bookmark, contentDescription = "Remove bookmark")
                }
                IconButton(onClick = onNoteClick) {
                    Icon(imageVector = if (bookmark.note.isBlank())
                        Icons.Outlined.EditNote
                    else
                        Icons.Filled.EditNote,
                            contentDescription = "Edit note")
                }
            }
        }
    }
}
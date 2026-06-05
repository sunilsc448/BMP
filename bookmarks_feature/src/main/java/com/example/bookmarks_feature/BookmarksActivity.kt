package com.example.bookmarks_feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.bmp.ui.theme.BMPTheme

class BookmarksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMPTheme {
                Text("Bookmarks Feature loaded dynamically! ✅")
            }
        }
    }
}
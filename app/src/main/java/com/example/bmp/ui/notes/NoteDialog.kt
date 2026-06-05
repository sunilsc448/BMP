package com.example.bmp.ui.notes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun NoteDialog(currentNote: String,
               onSave:(String) -> Unit,
               onDismiss:() -> Unit){
    var noteText by rememberSaveable { mutableStateOf(currentNote) }
    AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Private note") },
            text = {
                OutlinedTextField(value = noteText,
                        onValueChange = { noteText = it },
                        label = { Text("Write your note") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3)
            },
            confirmButton = {
                TextButton(onClick = { onSave(noteText) }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) { Text("Cancel") }
            }
    )
}
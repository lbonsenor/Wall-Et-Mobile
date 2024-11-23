package com.example.wall_et_mobile.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    visible: Boolean,
    message: String,
    onDismiss: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Error") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }
}
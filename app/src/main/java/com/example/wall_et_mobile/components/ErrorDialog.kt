package com.example.wall_et_mobile.components

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wall_et_mobile.R

@Composable
fun ErrorDialog(
    visible: Boolean,
    message: String,
    onDismiss: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = stringResource(R.string.error),
                    color = MaterialTheme.colorScheme.error
                )
            },
            text = {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        stringResource(R.string.accept),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorDialogPreview() {
    ErrorDialog(
        visible = true,
        message = stringResource(R.string.unexpected_error),
        onDismiss = {}
    )
}
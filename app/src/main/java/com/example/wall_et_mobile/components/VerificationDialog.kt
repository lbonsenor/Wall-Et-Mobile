package com.example.wall_et_mobile.components

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.ui.theme.WallEtTheme

@Composable
fun VerificationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { 
                onDismiss()
                onConfirm()
            },
            title = { Text(stringResource(R.string.verification_email)) },
            text = { Text(stringResource(R.string.verification_email_sent), color = MaterialTheme.colorScheme.onBackground) },
            confirmButton = {
                Button(onClick = {
                    onDismiss()
                    onConfirm()
                }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }
}

@Preview(name = "LightMode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DarkMode")
@Composable
fun VerificationDialogPreview() {
    WallEtTheme {
        VerificationDialog(
            showDialog = true,
            onDismiss = {},
            onConfirm = {}
        )
    }
}
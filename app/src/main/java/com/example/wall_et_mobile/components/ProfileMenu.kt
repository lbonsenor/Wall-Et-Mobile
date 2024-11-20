package com.example.wall_et_mobile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import kotlin.reflect.KProperty


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileOptionsDialog(
    userEmail: String,
    userName: String,
    cvuAlias: String,
    onDismiss: () -> Unit,
    onManageProfile: () -> Unit,
    onSettings: () -> Unit,
    onHelp: () -> Unit
) {
    var showCopyConfirmation by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = 3.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape).padding(8.dp)
                        )
                        Column() {
                            Text(
                                text = userName,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = userEmail,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }


                    }

                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Person,
                    text = "Manage your Profile",
                    onClick = onManageProfile
                )

                ProfileMenuItem(
                    icon = Icons.Default.Share,
                    text = "Copy CVU/Alias",
                    onClick = {
                        clipboardManager.setText(AnnotatedString(cvuAlias))
                        showCopyConfirmation = true
                    }
                )

                ProfileMenuItem(
                    icon = Icons.Default.Settings,
                    text = "Wall-Et Settings",
                    onClick = onSettings
                )

                ProfileMenuItem(
                    icon = Icons.Default.AccountBox,
                    text = "Help",
                    onClick = onHelp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = {  }) {
                        Text(text = "Privacy Policy", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    }
                    Text("•")
                    TextButton(onClick = {  }) {
                        Text(text = "Terms of Service", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    }
                }
            }
        }
    }

    if (showCopyConfirmation) {
        SnackbarHost(
            hostState = remember { SnackbarHostState() }
        ) {
            Snackbar(
                action = {
                    TextButton(onClick = { showCopyConfirmation = false }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text("CVU/Alias copied to clipboard")
            }
        }
    }
}


@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun ProfileOptionsDialogPreview() {
    WallEtTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            ProfileOptionsDialog(
                userEmail = "example@email.com",
                userName = "Camila Lee",
                cvuAlias = "your-cvu-alias-here",
                onDismiss = {  },
                onManageProfile = { /* Handle profile management */ },
                onSettings = { /* Handle settings */ },
                onHelp = { /* Handle help */ }
            )
        }
    }
}

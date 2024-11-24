package com.example.wall_et_mobile.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ConfirmationDialog
import com.example.wall_et_mobile.components.EndFormButton
import com.example.wall_et_mobile.components.ErrorDialog

@Composable
fun ProfileScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showAliasDialog by remember { mutableStateOf(false) }
    var newAlias by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isAuthenticated) {
        if (!uiState.isAuthenticated) {
            onNavigateToLogin()
        }
    }

    if (uiState.error != null) {
        val errorMessage = when (uiState.error.message) {
            "Missing dot in between words" -> stringResource(R.string.invalid_alias)
            else -> stringResource(R.string.unexpected_error)
        }
    
        ErrorDialog(
            visible = true,
            message = errorMessage,
            onDismiss = { viewModel.clearError() }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (uiState.isFetching) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        } else {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.name),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = uiState.user?.name ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.last_name),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = uiState.user?.lastName ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.email),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = uiState.user?.email ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.cbu),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = uiState.wallet?.cbu ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Column {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = stringResource(R.string.alias),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Text(
                                        text = uiState.wallet?.alias ?: "",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                IconButton(
                                    onClick = { 
                                        newAlias = uiState.wallet?.alias ?: ""
                                        showAliasDialog = true
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Edit alias",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            EndFormButton(
                onClick = { },
                textResourceId = R.string.reset_password
            )

            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                )
            ) {
                Text(text = stringResource(R.string.log_out))
            }
        }

        if (showLogoutDialog) {
            ConfirmationDialog(
                title = stringResource(R.string.log_out),
                text = stringResource(R.string.log_out_confirmation),
                onConfirm = {
                    showLogoutDialog = false
                    viewModel.logout()
                },
                onDismiss = { showLogoutDialog = false },
                confirmText = stringResource(R.string.exit),
                dismissText = stringResource(android.R.string.cancel)
            )
        }

        if (showAliasDialog) {
            AlertDialog(
                onDismissRequest = {
                    showAliasDialog = false
                    newAlias = ""
                },
                title = {
                    Text(
                        text = stringResource(R.string.modify_alias),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                text = {
                    OutlinedTextField(
                        value = newAlias,
                        onValueChange = { newAlias = it },
                        label = { Text(stringResource(R.string.new_alias)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newAlias.isNotEmpty()) {
                                viewModel.updateAlias(newAlias)
                                showAliasDialog = false
                                newAlias = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(stringResource(R.string.update))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showAliasDialog = false
                            newAlias = ""
                        }
                    ) {
                        Text(
                            stringResource(android.R.string.cancel),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun ProfilePreview(){
    ProfileScreen(onNavigateToLogin = {})
}
package com.example.wall_et_mobile.screens.profile

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ConfirmationDialog
import com.example.wall_et_mobile.components.PasswordField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showAliasDialog by remember { mutableStateOf(false) }
    var newAlias by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    var showResetPassword by remember { mutableStateOf(false) }
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(uiState.isAuthenticated) {
        if (!uiState.isAuthenticated) {
            onNavigateToLogin()
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp)
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                        .padding(24.dp),
                    //.horizontalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    if (uiState.isFetching) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                ProfileField(
                                    label = stringResource(R.string.name),
                                    value = uiState.user?.name ?: "",
                                )

                                ProfileField(
                                    label = stringResource(R.string.last_name),
                                    value = uiState.user?.lastName ?: "",
                                )

                                ProfileField(
                                    label = stringResource(R.string.email),
                                    value = uiState.user?.email ?: "",
                                )

                                CbuField(
                                    uiState.wallet?.cbu ?: "",
                                    snackbarHostState
                                )
                                HorizontalDivider(
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                )
                                AliasField(
                                    alias = uiState.wallet?.alias ?: "",
                                    onEditClick = {
                                        newAlias = uiState.wallet?.alias ?: ""
                                        showAliasDialog = true
                                    },
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { showResetPassword = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Icon(
                                    Icons.Filled.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.reset_password),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            Button(
                                onClick = { showLogoutDialog = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.error
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.logout),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.log_out),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
        )

    if (showLogoutDialog) {
        ConfirmationDialog(
            title = stringResource(R.string.log_out),
            text = stringResource(R.string.log_out_confirmation),
            onConfirm = {
                showLogoutDialog = false
                viewModel.logout()
                onNavigateToLogin()
            },
            onDismiss = { showLogoutDialog = false },
            confirmText = stringResource(R.string.exit),
            dismissText = stringResource(android.R.string.cancel),
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
                    style = MaterialTheme.typography.titleLarge,
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
            }
        )
    }
    if (showResetPassword) {
        AlertDialog(
            onDismissRequest = {
                showResetPassword = false
                newPassword = ""
                confirmPassword = ""
            },
            title = {
                Text(
                    text = stringResource(R.string.reset_password),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PasswordField(
                        password = newPassword,
                        onPasswordChange = { newPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = R.string.password,
                        errorMessage = stringResource(R.string.password_format),
                        validate = { newPassword -> validatePassword(newPassword) }
                    )

                    PasswordField(
                        password = confirmPassword,
                        onPasswordChange = { confirmPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        isRepeatPassword = true,
                        originalPassword = newPassword,
                        label = R.string.repeat,
                        errorMessage = stringResource(R.string.passwords_dont_match)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if ( validatePassword(newPassword) && newPassword == confirmPassword) {
                            viewModel.resetPassword(newPassword)
                            showResetPassword = false
                            newPassword = ""
                            confirmPassword = ""
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
                        showResetPassword = false
                        newPassword = ""
                        confirmPassword = ""
                    }
                ) {
                    Text(
                        stringResource(android.R.string.cancel),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        )
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun CbuField(
    cbu: String,
    snackbarHostState: SnackbarHostState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.cbu),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
            )
            CopyButton(
                cbu, snackbarHostState
            )
        }
        Text(
            text = cbu,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun AliasField(
    alias: String,
    onEditClick: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.alias),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit alias",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(30.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                            .padding(4.dp)
                    )
                }
                CopyButton(alias, snackbarHostState)
            }
        }

            Text(
                text = alias,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )


    }
}

fun validatePassword(password: String): Boolean {
    val passwordPattern = "^(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{8,}$"
    return password.matches(passwordPattern.toRegex())
}

@Composable 
fun CopyButton(
    content: String,
    snackbarHostState: SnackbarHostState
) {
    val clipboardManager = LocalClipboardManager.current
    val message = "$content ${stringResource(R.string.copied_to_clipboard)}"
    IconButton(
        onClick = {
            clipboardManager.setText(AnnotatedString(content))
            CoroutineScope(Dispatchers.Main).launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
            }
                  },
        modifier = Modifier.size(32.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.copy),
            contentDescription = "Copy alias",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(30.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                .padding(4.dp)

        )
    }
}
//}

//    if (uiState.error != null) {
//        val errorMessage = when (uiState.error.message) {
//            "Missing dot in between words" -> stringResource(R.string.invalid_alias)
//            else -> stringResource(R.string.unexpected_error)
//        }
//
//        ErrorDialog(
//            visible = true,
//            message = errorMessage,
//            onDismiss = { viewModel.clearError() }
//        )
//    }
//
//    if (isLandscape) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background)
//                .padding(64.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            if (uiState.isFetching) {
//                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
//            } else {
//                Card(
//                    modifier = Modifier.weight(0.6f),
//                    shape = RoundedCornerShape(20.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = MaterialTheme.colorScheme.background
//                    ),
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(24.dp)
//                        ) {
//                            Column(modifier = Modifier.weight(1f)) {
//                                Text(
//                                    text = stringResource(R.string.name),
//                                    style = MaterialTheme.typography.bodyMedium,
//                                    color = MaterialTheme.colorScheme.secondary
//                                )
//                                Text(
//                                    text = uiState.user?.name ?: "",
//                                    style = MaterialTheme.typography.bodyLarge,
//                                    color = MaterialTheme.colorScheme.onSurface
//                                )
//                            }
//
//                            Column(modifier = Modifier.weight(1f)) {
//                                Text(
//                                    text = stringResource(R.string.last_name),
//                                    style = MaterialTheme.typography.bodyMedium,
//                                    color = MaterialTheme.colorScheme.secondary
//                                )
//                                Text(
//                                    text = uiState.user?.lastName ?: "",
//                                    style = MaterialTheme.typography.bodyLarge,
//                                    color = MaterialTheme.colorScheme.onSurface
//                                )
//                            }
//                        }
//
//                        Column {
//                            Text(
//                                text = stringResource(R.string.email),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.secondary
//                            )
//                            Text(
//                                text = uiState.user?.email ?: "",
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//
//                        Column {
//                            Text(
//                                text = stringResource(R.string.cbu),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.secondary
//                            )
//                            Text(
//                                text = uiState.wallet?.cbu ?: "",
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//
//                        Column {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Column {
//                                    Text(
//                                        text = stringResource(R.string.alias),
//                                        style = MaterialTheme.typography.bodyMedium,
//                                        color = MaterialTheme.colorScheme.secondary
//                                    )
//                                    Text(
//                                        text = uiState.wallet?.alias ?: "",
//                                        style = MaterialTheme.typography.bodyLarge,
//                                        color = MaterialTheme.colorScheme.onSurface
//                                    )
//                                }
//                                IconButton(
//                                    onClick = {
//                                        newAlias = uiState.wallet?.alias ?: ""
//                                        showAliasDialog = true
//                                    }
//                                ) {
//                                    Icon(
//                                        Icons.Default.Edit,
//                                        contentDescription = "Edit alias",
//                                        tint = MaterialTheme.colorScheme.secondary
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//
//                Column(
//                    modifier = Modifier
//                        .weight(0.4f)
//                        .padding(vertical = 16.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    EndFormButton(
//                        onClick = { },
//                        textResourceId = R.string.reset_password,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Button(
//                        onClick = { showLogoutDialog = true },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp),
//                        shape = RoundedCornerShape(20.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.error,
//                            contentColor = MaterialTheme.colorScheme.onSecondary,
//                        )
//                    ) {
//                        Text(text = stringResource(R.string.log_out))
//                    }
//                }
//            }
//        }
//    } else {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background)
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState()),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            if (uiState.isFetching) {
//                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
//            } else {
//                Card(
//                    shape = RoundedCornerShape(20.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = MaterialTheme.colorScheme.background
//                    ),
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        Column {
//                            Text(
//                                text = stringResource(R.string.name),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.secondary
//                            )
//                            Text(
//                                text = uiState.user?.name ?: "",
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//
//                        Column {
//                            Text(
//                                text = stringResource(R.string.last_name),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.secondary
//                            )
//                            Text(
//                                text = uiState.user?.lastName ?: "",
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//
//                        Column {
//                            Text(
//                                text = stringResource(R.string.email),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.secondary
//                            )
//                            Text(
//                                text = uiState.user?.email ?: "",
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//
//                        Column {
//                            Text(
//                                text = stringResource(R.string.cbu),
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.secondary
//                            )
//                            Text(
//                                text = uiState.wallet?.cbu ?: "",
//                                style = MaterialTheme.typography.bodyLarge,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//
//                        Column {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Column {
//                                    Text(
//                                        text = stringResource(R.string.alias),
//                                        style = MaterialTheme.typography.bodyMedium,
//                                        color = MaterialTheme.colorScheme.secondary
//                                    )
//                                    Text(
//                                        text = uiState.wallet?.alias ?: "",
//                                        style = MaterialTheme.typography.bodyLarge,
//                                        color = MaterialTheme.colorScheme.onSurface
//                                    )
//                                }
//                                IconButton(
//                                    onClick = {
//                                        newAlias = uiState.wallet?.alias ?: ""
//                                        showAliasDialog = true
//                                    }
//                                ) {
//                                    Icon(
//                                        Icons.Default.Edit,
//                                        contentDescription = "Edit alias",
//                                        tint = MaterialTheme.colorScheme.secondary
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//
//                EndFormButton(
//                    onClick = { },
//                    textResourceId = R.string.reset_password
//                )
//
//                Button(
//                    onClick = { showLogoutDialog = true },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp),
//                    shape = RoundedCornerShape(20.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.error,
//                        contentColor = MaterialTheme.colorScheme.onSecondary,
//                    )
//                ) {
//                    Text(text = stringResource(R.string.log_out))
//                }
//            }
//        }
//    }
//
//    if (showLogoutDialog) {
//        ConfirmationDialog(
//            title = stringResource(R.string.log_out),
//            text = stringResource(R.string.log_out_confirmation),
//            onConfirm = {
//                showLogoutDialog = false
//                viewModel.logout()
//                onNavigateToLogin()
//            },
//            onDismiss = { showLogoutDialog = false },
//            confirmText = stringResource(R.string.exit),
//            dismissText = stringResource(android.R.string.cancel)
//        )
//    }
//
//    if (showAliasDialog) {
//        AlertDialog(
//            onDismissRequest = {
//                showAliasDialog = false
//                newAlias = ""
//            },
//            title = {
//                Text(
//                    text = stringResource(R.string.modify_alias),
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            },
//            text = {
//                OutlinedTextField(
//                    value = newAlias,
//                    onValueChange = { newAlias = it },
//                    label = { Text(stringResource(R.string.new_alias)) },
//                    singleLine = true,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        if (newAlias.isNotEmpty()) {
//                            viewModel.updateAlias(newAlias)
//                            showAliasDialog = false
//                            newAlias = ""
//                        }
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.secondary
//                    )
//                ) {
//                    Text(stringResource(R.string.update))
//                }
//            },
//            dismissButton = {
//                TextButton(
//                    onClick = {
//                        showAliasDialog = false
//                        newAlias = ""
//                    }
//                ) {
//                    Text(
//                        stringResource(android.R.string.cancel),
//                        color = MaterialTheme.colorScheme.secondary
//                    )
//                }
//            },
//            containerColor = MaterialTheme.colorScheme.surface,
//            shape = RoundedCornerShape(16.dp)
//        )
//    }
//}

@Preview
@Composable
fun ProfilePreview() {
    ProfileScreen(onNavigateToLogin = {})
}
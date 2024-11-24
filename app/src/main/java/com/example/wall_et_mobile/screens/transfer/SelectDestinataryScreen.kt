package com.example.wall_et_mobile.screens.transfer


import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ContactListWithSearchBar
import com.example.wall_et_mobile.components.ContactsTabs
import com.example.wall_et_mobile.components.TransferProgress
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.User

@Composable
fun SelectDestinataryScreen(
    innerPadding : PaddingValues,
    onNavigateToSelectAmount: (String) -> Unit,
    viewModel: TransferViewModel = viewModel(factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getPayments(
            page = 1,
            direction = "DESC",
            pending = null,
            type = null,
            range = null,
            source = null,
            cardId = null
        )
    }

    var currentTab by remember { mutableIntStateOf(0) }
    var favoriteUserIds by remember { mutableStateOf<List<Int>>(emptyList()) }
    var contactValue by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TransferProgress(0)
        OutlinedTextField(
            value = contactValue,
            onValueChange = { contactValue = it  },
            isError = contactValue.isNotEmpty() && !isValidEmail(contactValue),
            placeholder = {
                Text(
                    stringResource(R.string.transfer_to_1),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = stringResource(R.string.transfer_to_1),
                    modifier = Modifier.padding(start = 15.dp)
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button (
            onClick = {
                    onNavigateToSelectAmount(contactValue)
            },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shape = RoundedCornerShape(10.dp),
            enabled = contactValue.isNotEmpty() && isValidEmail(contactValue),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(stringResource(R.string.continue_button))
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text(stringResource(R.string.error)) },
                text = { Text(stringResource(R.string.user_not_found)) },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }

        ContactsTabs(onTabSelected = { tabIndex -> currentTab = tabIndex }, initialTab = currentTab)
        when (currentTab) {
            0 -> ContactListWithSearchBar(
                getRecentContacts(uiState.transactions),
                favoriteUserIds,
                { userId, isFavorite -> favoriteUserIds = if (isFavorite) { favoriteUserIds + userId } else { favoriteUserIds - userId } },
                onClick = onNavigateToSelectAmount
            )


            1 -> ContactListWithSearchBar(
                getRecentContacts(uiState.transactions).filter { it.id in favoriteUserIds },
                favoriteUserIds,
                { userId, isFavorite -> favoriteUserIds = if (isFavorite) { favoriteUserIds + userId } else { favoriteUserIds - userId }},
                onClick = onNavigateToSelectAmount
            )
        }
    }

}

fun getRecentContacts(transactions: List<Transaction>) : List<User> {
    val recentContacts = mutableListOf<User>()

    transactions.forEach { transaction ->
        if (!recentContacts.contains(transaction.receiver)) {
            recentContacts.add(transaction.receiver)
        }
    }

    return recentContacts
}

@Composable
fun SelectDestinataryScreenLandscape(
    innerPadding : PaddingValues,
    onNavigateToSelectAmount: (String) -> Unit,
    viewModel: TransferViewModel = viewModel(factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getPayments(
            page = 1,
            direction = "DESC",
            pending = null,
            type = null,
            range = null,
            source = null,
            cardId = null
        )
    }

    var currentTab by remember { mutableIntStateOf(0) }
    var favoriteUserIds by remember { mutableStateOf<List<Int>>(emptyList()) }
    var contactValue by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row (horizontalArrangement = Arrangement.SpaceBetween) {
            Column (modifier = Modifier.weight(0.4f).fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
                TransferProgress(0)
                OutlinedTextField(
                    value = contactValue,
                    onValueChange = { contactValue = it  },
                    isError = contactValue.isNotEmpty() && !isValidEmail(contactValue),
                    placeholder = {
                        Text(
                            stringResource(R.string.transfer_to_1),
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Face,
                            contentDescription = stringResource(R.string.transfer_to_1),
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Button (
                    onClick = {
                        onNavigateToSelectAmount(contactValue)
                    },
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(10.dp),
                    enabled = contactValue.isNotEmpty() && isValidEmail(contactValue),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text(stringResource(R.string.continue_button))
                }

                if (showErrorDialog) {
                    AlertDialog(
                        onDismissRequest = { showErrorDialog = false },
                        title = { Text(stringResource(R.string.error)) },
                        text = { Text(stringResource(R.string.user_not_found)) },
                        confirmButton = {
                            Button(onClick = { showErrorDialog = false }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }

            Column (modifier = Modifier.weight(0.4f)) {
                ContactsTabs(onTabSelected = { tabIndex -> currentTab = tabIndex }, initialTab = currentTab)
                when (currentTab) {
                    0 -> ContactListWithSearchBar(
                        getRecentContacts(uiState.transactions),
                        favoriteUserIds,
                        { userId, isFavorite -> favoriteUserIds = if (isFavorite) { favoriteUserIds + userId } else { favoriteUserIds - userId } },
                        onClick = onNavigateToSelectAmount
                    )


                    1 -> ContactListWithSearchBar(
                        getRecentContacts(uiState.transactions).filter { it.id in favoriteUserIds },
                        favoriteUserIds,
                        { userId, isFavorite -> favoriteUserIds = if (isFavorite) { favoriteUserIds + userId } else { favoriteUserIds - userId }},
                        onClick = onNavigateToSelectAmount
                    )
                }
            }
        }
    }

}


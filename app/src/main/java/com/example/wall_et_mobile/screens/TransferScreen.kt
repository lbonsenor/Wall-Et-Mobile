package com.example.wall_et_mobile.screens


import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ContactListWithSearchBar
import com.example.wall_et_mobile.components.ContactsTabs
import com.example.wall_et_mobile.components.TransferProgress
import com.example.wall_et_mobile.data.mock.MockContacts

@Composable
fun TransferScreen(innerPadding : PaddingValues) {
    var currentTab by remember { mutableStateOf(0) }
    var favoriteUserIds by remember { mutableStateOf<List<Int>>(emptyList()) }
    var contactValue by remember { mutableStateOf("") }

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
            onValueChange = { contactValue = it },
            placeholder = {
                Text(
                    stringResource(R.string.transfer_to_1),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = "Search CVU or alias",
                    modifier = Modifier.padding(start = 15.dp)
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button (
            onClick = {  },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(stringResource(R.string.continue_button))
        }
        ContactsTabs(onTabSelected = { tabIndex -> currentTab = tabIndex }, initialTab = currentTab)
        when (currentTab) {
            0 -> ContactListWithSearchBar(MockContacts.sampleContacts, favoriteUserIds)
            { userId, isFavorite -> favoriteUserIds = if (isFavorite) { favoriteUserIds + userId } else { favoriteUserIds - userId } }

            1 -> ContactListWithSearchBar(MockContacts.sampleContacts.filter { it.id in favoriteUserIds },
                favoriteUserIds
            ) { userId, isFavorite -> favoriteUserIds = if (isFavorite) {
                    favoriteUserIds + userId
                } else {
                    favoriteUserIds - userId
                }
            }
        }
    }

}
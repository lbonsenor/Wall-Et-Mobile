package com.example.wall_et_mobile.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.components.ContactListWithSearchBar
import com.example.wall_et_mobile.components.ContactsTabs
import com.example.wall_et_mobile.data.mock.MockContacts

@Composable
fun TransferScreen(innerPadding : PaddingValues) {
    var currentTab by remember { mutableStateOf(0) }
    var favoriteUserIds by remember { mutableStateOf<List<Int>>(emptyList()) }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
    ) {

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
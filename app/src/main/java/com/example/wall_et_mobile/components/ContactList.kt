package com.example.wall_et_mobile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.model.User

@Composable
fun ContactsTabs(onTabSelected: (Int) -> Unit, initialTab: Int = 0) {
    var selectedTabIndex by remember { mutableStateOf(initialTab) }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )}
        ) {
        Tab(
            selected = selectedTabIndex == 0,
            text = { Text(stringResource(R.string.recent_contacts)) },
            onClick = {
                selectedTabIndex = 0
                onTabSelected(0)},
        )
        Tab(
            selected = selectedTabIndex == 1,
            text = { Text(stringResource(R.string.favorite_contacts)) },
            onClick = {
                selectedTabIndex = 1
                onTabSelected(1)},
        )
    }
}
@Composable
fun ContactListWithSearchBar(contacts: List<User>, favoriteUserIds: List<Int>, onFavoriteChange: (Int, Boolean) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredContacts = contacts.filter { contact -> contact.name.contains(searchQuery, ignoreCase = true) || contact.lastName.contains(searchQuery, ignoreCase = true) }
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        ContactList(filteredContacts, favoriteUserIds, onFavoriteChange)

    }
}
@Composable
fun ContactList(contacts: List<User>, favoriteUserIds: List<Int>, onFavoriteChange: (Int, Boolean) -> Unit) {
    contacts.forEach { contact ->
        ContactItem(user = contact, favoriteUserIds = favoriteUserIds, onFavoriteChange = onFavoriteChange)
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceVariant,
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(R.string.search_contact), color = MaterialTheme.colorScheme.onTertiary) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", modifier = Modifier.padding(start = 15.dp)) },
        modifier = modifier,
        singleLine = true,
        shape = CircleShape
    )
}
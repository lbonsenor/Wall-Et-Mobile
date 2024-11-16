package com.example.wall_et_mobile.screens

import MockTransactions
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import com.example.wall_et_mobile.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.components.ActivityList

@Composable
fun ActivitiesScreen(innerPadding: PaddingValues) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredActivities = MockTransactions.sampleTransactions
        .filter { activity ->
            searchQuery.isEmpty() || activity.name.contains(searchQuery, ignoreCase = true)
        }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        ActivityList(activities = filteredActivities)
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(R.string.search_activities)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        modifier = modifier,
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}
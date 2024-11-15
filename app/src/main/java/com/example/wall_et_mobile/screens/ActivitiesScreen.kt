package com.example.wall_et_mobile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.components.Activity
import com.example.wall_et_mobile.components.ActivityItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ActivitiesScreen(innerPadding: PaddingValues) {
    var searchQuery by remember { mutableStateOf("") }

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

        ActivityList(searchQuery)
    }
}

@Composable
fun ActivityList(searchQuery: String = "") {
    val filteredActivities = Activity.sampleTransactions
        .filter { activity ->
            searchQuery.isEmpty() || activity.name.contains(searchQuery, ignoreCase = true)
        }
        .groupBy { activity ->
            activity.transactionTime.toString().split(" ")[0]
        }
        .toSortedMap(compareByDescending { it })

    filteredActivities.forEach { (dateStr, activities) ->
        val date = LocalDate.parse(dateStr)
        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")).replaceFirstChar { it.uppercase() }

        DateSeparator(formattedDate)

        activities.forEach { activity ->
            ActivityItem(activity = activity)
        }
    }
}

@Composable
fun DateSeparator(date: String) {
    Text(
        text = date,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Buscar transacciones...") },                                  // TODO Localización
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        modifier = modifier,
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}
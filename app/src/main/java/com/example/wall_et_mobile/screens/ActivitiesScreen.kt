package com.example.wall_et_mobile.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ActivityListWithDates
import com.example.wall_et_mobile.data.model.FilterDateType
import com.example.wall_et_mobile.data.model.Transaction

@Composable
fun ActivitiesScreen(innerPadding: PaddingValues) {
    var searchQuery by remember { mutableStateOf("") }
    var currentFilter by remember { mutableIntStateOf(4) }

    var activities = listOf<Transaction>()

    val filteredActivities = activities
        .filter { activity ->
            val filter : FilterDateType = when (currentFilter){
                0 -> FilterDateType.TODAY
                1 -> FilterDateType.LAST_WEEK
                2 -> FilterDateType.LAST_MONTH
                3 -> FilterDateType.LAST_YEAR
                4 -> FilterDateType.MAX
                else -> FilterDateType.MAX
            }
            (searchQuery.isEmpty() || activity.name.contains(searchQuery, ignoreCase = true))
//                && filter.inRange(activity.toLocalDate())
        }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
        )
        ActivityChipGroup(currentFilter, { currentFilter = it })
        ActivityListWithDates(activities = filteredActivities)
    }
}

@Composable
fun ActivityChipGroup(value: Int, onValueChange: (Int) -> Unit){
    Row (
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FilterDateType.entries.forEachIndexed { index, entry ->
            FilterChip(
                selected = value == index,
                onClick = {
                    onValueChange(index)
                },
                label = {
                    Text(
                        text = stringResource(entry.labelInt),
                    )},
                shape = CircleShape,
                colors = FilterChipDefaults.filterChipColors().copy(
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(R.string.search_activities), color = MaterialTheme.colorScheme.onTertiary) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", modifier = Modifier.padding(start = 15.dp)) },
        modifier = modifier,
        singleLine = true,
        shape = CircleShape
    )
}
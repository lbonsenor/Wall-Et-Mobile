package com.example.wall_et_mobile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.components.Activity
import com.example.wall_et_mobile.components.ActivityItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun ActivitiesScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        ActivityList()
    }
}

@Composable
fun ActivityList() {
    val groupedActivities = Activity.sampleTransactions
        .groupBy { activity -> 
            activity.transactionTime.toString().split(" ")[0]
        }
        .toSortedMap(compareByDescending { it })

    groupedActivities.forEach { (dateStr, activities) ->
        val date = LocalDate.parse(dateStr)
        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))

        DateSeparator(formattedDate);

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
package com.example.wall_et_mobile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.model.Activity

@Composable
fun ActivityList(activities: List<Activity>) {
//    val groupedActivities = activities
//        .groupBy { activity ->
//            activity.transactionTime.toString().split(" ")[0]
//        }
//        .toSortedMap(compareByDescending { it })
//
//    groupedActivities.forEach { (dateStr, activities) ->
//        val date = LocalDate.parse(dateStr)
//        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
//            .replaceFirstChar { it.uppercase() }
//
//        DateSeparator(formattedDate)
//
//
//    }
    Column() {
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

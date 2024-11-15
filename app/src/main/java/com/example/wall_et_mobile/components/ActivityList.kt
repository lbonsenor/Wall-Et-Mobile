package com.example.wall_et_mobile.components

import androidx.compose.runtime.Composable
import com.example.wall_et_mobile.screens.DateSeparator
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ActivityList(activities: List<Activity>) {
    val groupedActivities = activities
        .groupBy { activity ->
            activity.transactionTime.toString().split(" ")[0]
        }
        .toSortedMap(compareByDescending { it })

    groupedActivities.forEach { (dateStr, activities) ->
        val date = LocalDate.parse(dateStr)
        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
            .replaceFirstChar { it.uppercase() }

        DateSeparator(formattedDate)

        activities.forEach { activity ->
            ActivityItem(activity = activity)
        }
    }
}
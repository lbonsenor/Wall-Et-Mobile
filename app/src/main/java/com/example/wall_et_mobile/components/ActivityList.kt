package com.example.wall_et_mobile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.model.Transaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ActivityList(activities: List<Transaction>) {
    Column() {
        activities.forEach { activity ->
            ActivityItem(transaction = activity)
        }
    }

}

@Composable
fun ActivityListWithDates(activities: List<Transaction>){
    val groupedActivities = activities
        .groupBy { activity ->
            activity.transactionTime.toString().split(" ")[0]
        }
        .toSortedMap(compareByDescending { it })

    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        groupedActivities.forEach { (dateStr, activities) ->
            val date = LocalDate.parse(dateStr)
            val formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
                .replaceFirstChar { it.uppercase() }

            DateSeparator(formattedDate)
            Column() {
                activities.forEach { activity ->
                    ActivityItem(transaction = activity)
                }
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onTertiary,
                thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp)
            )
        }
    }


}

@Composable
fun DateSeparator(date: String) {
    Text(
        text = date,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onTertiary
    )
}

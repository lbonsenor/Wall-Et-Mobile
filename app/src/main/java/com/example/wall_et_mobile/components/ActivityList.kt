package com.example.wall_et_mobile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.R

@Composable
fun ActivityList(activities: List<Transaction>) {
    if (activities.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.no_activities),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        Column {
            activities.forEach { activity ->
                ActivityItem(transaction = activity)
            }
        }
    }
}

@Composable
fun ActivityListWithDates(activities: List<Transaction>){
    val groupedActivities = activities
        .groupBy { activity ->
            activity.createdAt // Obs i changed this because of API constraints
        }
        .toSortedMap(compareByDescending { it })

    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        groupedActivities.forEach { (date, activities) ->
            val sortedActivities = activities.sortedByDescending { it.transactionId }
            val dateStr =
                date.toString().split(" ")[1] + " " +
                date.toString().split(" ")[2] + " "

            DateSeparator(dateStr)
            Column() {
                sortedActivities.forEach { activity ->
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

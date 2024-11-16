package com.example.wall_et_mobile.screens

import MockTransactions
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ActivityList
import com.example.wall_et_mobile.components.Balance

@Composable
fun HomeScreen(innerPadding : PaddingValues){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ){
        Balance()

        // botones de transferencia e ingresar

        Column (
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp, 15.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                ) {
                Text(
                    text = stringResource(R.string.subtitle_recent_activities),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    modifier = Modifier
                        .clickable(enabled = true) {
                            Log.d("HomeScreen Click", "See more")
                        },
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                    text = "${stringResource(R.string.see_more)} >"
                )
            }
            ActivityList(MockTransactions.sampleTransactions)
        }
    }
}
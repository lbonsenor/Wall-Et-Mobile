package com.example.wall_et_mobile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wall_et_mobile.components.Activity
import com.example.wall_et_mobile.components.ActivityItem

@Composable
fun ActivitiesScreen(innerPadding : PaddingValues){
    Column (
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ){
        Activity.sampleTransactions.forEach { activity ->
            ActivityItem(activity = activity)
        }
    }
}
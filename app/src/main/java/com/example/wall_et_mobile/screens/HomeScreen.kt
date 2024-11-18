package com.example.wall_et_mobile.screens

import MockTransactions
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ActivityList
import com.example.wall_et_mobile.components.Balance
import com.example.wall_et_mobile.components.CustomButton
import com.example.wall_et_mobile.model.Screen

@Composable
fun HomeScreen(innerPadding : PaddingValues, navController : NavHostController){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ){
        BalanceCard(navController)

        Column (
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ActivityCard(navController)
        }
    }
}

@Composable
fun BalanceCard(navController : NavHostController){
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
            .background(MaterialTheme.colorScheme.primary)

    ) {
        Balance()
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(stringResource(R.string.title_transfer), R.drawable.transfer, onClick = {
                    navController.navigate(Screen.Transfer.route){
                        popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(stringResource(R.string.title_add_fund), R.drawable.receive, onClick = {})
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(
                    stringResource(R.string.invest),
                    R.drawable.invest,
                    onClick = {})
            }
        }
    }
}

@Composable
fun ActivityCard(navController: NavController){
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(20.dp)
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
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                modifier = Modifier
                    .clickable(enabled = true) {
                        navController.navigate(Screen.Activities.route){
                            popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.labelSmall,
                text = stringResource(R.string.see_more)
            )
        }
        ActivityList(MockTransactions.sampleTransactions.take(3))
    }
}

@Composable
fun HomeScreenLandscape(innerPadding: PaddingValues, navController: NavHostController){
    Row(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        BalanceCardLandscape(navController, innerPadding)
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ActivityCard(navController)
        }
    }
}

@Composable
fun BalanceCardLandscape(navController: NavController, innerPadding: PaddingValues){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxHeight()
            .width(350.dp)
            .clip(RoundedCornerShape(0.dp, 30.dp))
            .background(MaterialTheme.colorScheme.primary)

    ) {
        Balance()

        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(stringResource(R.string.title_transfer), R.drawable.transfer, onClick = {
                    navController.navigate(Screen.Transfer.route){
                        popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(stringResource(R.string.title_add_fund), R.drawable.receive, onClick = {})
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(
                    stringResource(R.string.invest),
                    R.drawable.invest,
                    onClick = {})
            }
        }
    }
}
package com.example.wall_et_mobile.screens.home

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.CustomTopAppBar
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.ActivityList
import com.example.wall_et_mobile.components.Balance
import com.example.wall_et_mobile.components.CustomButton
import com.example.wall_et_mobile.data.model.Transaction
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.data.model.Wallet


@Composable
fun HomeScreen(
    onNavigateToTransfer: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ){
        BalanceCard(
            onNavigateToTransfer = onNavigateToTransfer,
            onNavigateToTopUp = onNavigateToTopUp,
            onNavigateToProfile = onNavigateToProfile,
            wallet = uiState.wallet,
            isFetching = uiState.isFetching,
            error = uiState.error,
            user = uiState.user
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ActivityCard(onNavigateToActivity, uiState.transactions)
        }

    }
}

@Composable
fun BalanceCard(
    onNavigateToTransfer: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToProfile: () -> Unit,
    wallet: Wallet?,
    isFetching: Boolean,
    error: Error?,
    user: User?
){
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
            .background(MaterialTheme.colorScheme.primary)

    ) {
        CustomTopAppBar(user ?: User(1, "test", "test", "test", "test"), onNavigateToProfile)

        Balance(
            wallet = wallet,
            isFetching = isFetching,
            error = error
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(stringResource(R.string.title_transfer), R.drawable.transfer, onClick = onNavigateToTransfer
//                    navController.navigate(Screen.Transfer.route){
//                        popUpTo(navController.graph.findStartDestination().id) {saveState = true}
//                        launchSingleTop = true
//                        restoreState = true
//                    }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(stringResource(R.string.title_add_fund), R.drawable.receive, onClick = onNavigateToTopUp)
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(
                    stringResource(R.string.invest),
                    R.drawable.invest,
                    onClick = {},
                    enabled = false
                )
            }
        }
    }
}

@Composable
fun ActivityCard(onNavigateToActivity: () -> Unit, transactions : List<Transaction>){
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
                    .clickable(enabled = true, onClick = onNavigateToActivity)
//                        navController.navigate(Screen.Activities.route){
//                            popUpTo(navController.graph.findStartDestination().id) {saveState = true}
//                            launchSingleTop = true
//                            restoreState = true
//                        }
                    ,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.labelSmall,
                text = stringResource(R.string.see_more)
            )
        }
        ActivityList(transactions.sortedByDescending { it.transactionId }.take(3))
    }
}

@Composable
fun HomeScreenLandscape(
    innerPadding: PaddingValues,
    onNavigateToTransfer: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToTopUp: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
){
    val uiState by viewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        BalanceCardLandscape(onNavigateToTransfer, innerPadding, onNavigateToTopUp, onNavigateToProfile, uiState.wallet, uiState.isFetching, uiState.error, uiState.user)
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ActivityCard(onNavigateToActivity, uiState.transactions)
        }
    }
}

@Composable
fun BalanceCardLandscape(
    onNavigateToTransfer: () -> Unit,
    innerPadding: PaddingValues,
    onNavigateToTopUp: () -> Unit,
    onNavigateToProfile: () -> Unit,
    wallet: Wallet?,
    isFetching: Boolean,
    error: Error?,
    user: User?
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxHeight()
            .width(350.dp)
            .clip(RoundedCornerShape(0.dp, 30.dp))
            .background(MaterialTheme.colorScheme.primary)

    ) {
        Balance(
            wallet = wallet,
            isFetching = isFetching,
            error = error,
        )

        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                CustomButton(stringResource(R.string.title_transfer), R.drawable.transfer, onClick = onNavigateToTransfer
                )
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
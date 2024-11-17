package com.example.wall_et_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wall_et_mobile.screens.ActivitiesScreen
import com.example.wall_et_mobile.screens.HomeScreen
import com.example.wall_et_mobile.screens.BottomAppBarScreen
import com.example.wall_et_mobile.screens.Screen
import com.example.wall_et_mobile.screens.TransferScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var qrScanner = QRScanner(appContext = applicationContext)
            WallEtTheme {
                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()

                val statusBarColor = MaterialTheme.colorScheme.background
                val systemNavColor = MaterialTheme.colorScheme.primary

                SideEffect {
                    systemUiController.setStatusBarColor(statusBarColor)
                    systemUiController.setNavigationBarColor(systemNavColor)
                }

                Scaffold (
                    //topBar = { CustomTopAppBar(User(1, "test", "test", "Lautaro", "test", "test", "test", "test",)) },
                    bottomBar = { CustomAppBar(navController) },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = { QRFab(qrScanner::startScan) },
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.systemBarsPadding()

                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomAppBarScreen.Home.route,
                        builder = {
                            composable(BottomAppBarScreen.Home.route){ HomeScreen(innerPadding, navController) }
                            composable(BottomAppBarScreen.Cards.route){}
                            composable(BottomAppBarScreen.Activities.route){ ActivitiesScreen(innerPadding) }
                            composable(BottomAppBarScreen.SeeMore.route){}
                            composable(Screen.Transfer.route){ TransferScreen(innerPadding)}
                        }
                    )

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}










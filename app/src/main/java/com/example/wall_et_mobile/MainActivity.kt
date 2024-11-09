package com.example.wall_et_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wall_et_mobile.ui.theme.WallEtTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wall_et_mobile.screens.ActivitiesScreen
import com.example.wall_et_mobile.screens.HomeScreen
import com.example.wall_et_mobile.screens.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WallEtTheme {
                val navController = rememberNavController()
                Scaffold (
                    bottomBar = { CustomAppBar(navController) },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = { QRFab() },
                    backgroundColor = MaterialTheme.colorScheme.background

                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        builder = {
                            composable(Screen.Home.route){ HomeScreen(innerPadding) }
                            composable(Screen.Cards.route){}
                            composable(Screen.Activities.route){ ActivitiesScreen(innerPadding) }
                            composable(Screen.SeeMore.route){}
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





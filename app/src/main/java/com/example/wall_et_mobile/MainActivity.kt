package com.example.wall_et_mobile

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wall_et_mobile.ui.theme.WallEtTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wall_et_mobile.screens.ActivitiesScreen
import com.example.wall_et_mobile.screens.HomeScreen
import com.example.wall_et_mobile.model.Screen
import com.example.wall_et_mobile.screens.CardsScreen
import com.example.wall_et_mobile.screens.forgotPassword.ForgotPasswordScreen
import com.example.wall_et_mobile.screens.HomeScreenLandscape
import com.example.wall_et_mobile.screens.LoginScreen
import com.example.wall_et_mobile.screens.SignupScreen
import com.example.wall_et_mobile.screens.forgotPassword.NewPasswordScreen
import com.example.wall_et_mobile.screens.forgotPassword.VerificationCodeScreen
import com.example.wall_et_mobile.screens.transfer.SelectAmountScreen
import com.example.wall_et_mobile.screens.transfer.SelectPaymentScreen
import com.example.wall_et_mobile.screens.transfer.TransferScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.material3.Scaffold as Scaffold2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val (orientation, setOrientation) = remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }

            val configuration = LocalConfiguration.current

            LaunchedEffect(configuration) {
                snapshotFlow { configuration.orientation }
                    .collect { setOrientation(it) }
            }

            var qrScanner = QRScanner(appContext = applicationContext)
            WallEtTheme {
                val navController = rememberNavController()

                when (orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> ScaffoldPortrait(navController, qrScanner)
                    else -> ScaffoldLandscape(navController, qrScanner)
                }

            }
        }
    }
}

@Composable
fun ScaffoldPortrait(navController: NavHostController, qrScanner: QRScanner) {
    val systemUiController = rememberSystemUiController()

    val statusBarColor = MaterialTheme.colorScheme.background
    val systemNavColor = MaterialTheme.colorScheme.primary
    val currentRoute = currentRoute(navController)

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(systemNavColor)
    }
    Scaffold(
        //topBar = { CustomTopAppBar(User(1, "test", "test", "Lautaro", "test", "test", "test", "test",)) },
        bottomBar = {
            if (currentRoute != Screen.Login.route && currentRoute != Screen.Signup.route && currentRoute != Screen.ForgotPassword.route && currentRoute != Screen.VerificationCode.route && currentRoute != Screen.NewPassword.route) {
                CustomAppBar(navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (currentRoute != Screen.Login.route && currentRoute != Screen.Signup.route && currentRoute != Screen.ForgotPassword.route && currentRoute != Screen.VerificationCode.route && currentRoute != Screen.NewPassword.route) {
                QRFab(qrScanner::startScan)
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.systemBarsPadding()

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            builder = {
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(Screen.Signup.route) { SignupScreen(navController) }
                composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }
                composable(Screen.VerificationCode.route) { VerificationCodeScreen(navController) }
                composable(Screen.NewPassword.route) { NewPasswordScreen(navController) }
                composable(Screen.Home.route) { HomeScreen(innerPadding, navController) }
                composable(Screen.Cards.route) { CardsScreen(innerPadding) }
                composable(Screen.Activities.route) { ActivitiesScreen(innerPadding) }
                composable(Screen.SeeMore.route) {}
                composable(Screen.Transfer.route) { TransferScreen(innerPadding, navController) }
                composable(
                    route = Screen.SelectAmount.route,
                    arguments = listOf(
                        navArgument(name = "id") {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->
                    SelectAmountScreen(
                        innerPadding,
                        navController,
                        backStackEntry.arguments?.getInt("id")!!
                    )
                }
                composable(
                    route = Screen.SelectPaymentMethod.route,
                    arguments = listOf(
                        navArgument(name = "id") {
                            type = NavType.IntType
                        },
                        navArgument(name = "amount") {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    SelectPaymentScreen(
                        innerPadding,
                        navController,
                        backStackEntry.arguments?.getInt("id")!!,
                        backStackEntry.arguments?.getString("amount")!!
                    )
                }
            }
        )
    }
}

@Composable
fun ScaffoldLandscape(navController: NavHostController, qrScanner: QRScanner) {
    val systemUiController = rememberSystemUiController()

    val statusBarColor = MaterialTheme.colorScheme.primary
    val systemNavColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(systemNavColor)
    }
    Scaffold2(

    )
    { innerPadding ->
        Row(
            // modifier = Modifier.fillMaxSize().padding(horizontal = innerPadding.calculateRightPadding(layoutDirection = ))
        )
        {
            NavBarLandscape(navController, qrScanner)
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                builder = {
                    composable(Screen.Home.route) {
                        HomeScreenLandscape(
                            innerPadding,
                            navController
                        )
                    }
                    composable(Screen.Cards.route) { CardsScreen(innerPadding) }
                    composable(Screen.Activities.route) { ActivitiesScreen(innerPadding) }
                    composable(Screen.SeeMore.route) {}
                    composable(Screen.Transfer.route) {
                        TransferScreen(
                            innerPadding,
                            navController
                        )
                    }
                    composable(
                        route = Screen.SelectAmount.route,
                        arguments = listOf(
                            navArgument(name = "id") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        SelectAmountScreen(
                            innerPadding,
                            navController,
                            backStackEntry.arguments?.getInt("id")!!
                        )
                    }
                    composable(
                        route = Screen.SelectPaymentMethod.route,
                        arguments = listOf(
                            navArgument(name = "id") {
                                type = NavType.IntType
                            },
                            navArgument(name = "amount") {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        SelectPaymentScreen(
                            innerPadding,
                            navController,
                            backStackEntry.arguments?.getInt("id")!!,
                            backStackEntry.arguments?.getString("amount")!!
                        )
                    }
                }
            )
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










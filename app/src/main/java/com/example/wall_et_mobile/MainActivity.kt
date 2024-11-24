package com.example.wall_et_mobile

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.systemBarsPadding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wall_et_mobile.data.model.Screen
import com.example.wall_et_mobile.data.model.Screen.Activities
import com.example.wall_et_mobile.data.model.Screen.Cards
import com.example.wall_et_mobile.data.model.Screen.Home
import com.example.wall_et_mobile.data.model.Screen.SeeMore
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.Locale
import androidx.compose.material3.Scaffold as Scaffold2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val (orientation, setOrientation) = remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
            val configuration = LocalConfiguration.current
            val navController = rememberNavController()

            LaunchedEffect(configuration) {
                snapshotFlow { configuration.orientation }
                    .collect { setOrientation(it) }
            }

            var qrScanner = QRScanner(appContext = applicationContext)

            WallEtTheme {
                when (orientation){
                    Configuration.ORIENTATION_PORTRAIT -> ScaffoldPortrait(navController, qrScanner)
                    else -> ScaffoldLandscape(navController, qrScanner)
                }

            }
        }
    }
}

@Composable
fun ScaffoldPortrait(navController: NavHostController, qrScanner: QRScanner){
    val systemUiController = rememberSystemUiController()

    val statusBarColor = MaterialTheme.colorScheme.background
    val systemNavColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(systemNavColor)
    }
    Scaffold (
        bottomBar = {
            when (currentRoute(navController)) {
                Home.route -> CustomAppBar(navController)
                Cards.route -> CustomAppBar(navController)
                Activities.route -> CustomAppBar(navController)
                SeeMore.route -> CustomAppBar(navController)
                else -> {}
            }
        },
        topBar = {
            val route = currentRoute(navController)?.split("/")?.first()

            val noTopBarRoutes = listOf(
                Screen.Home.route,
                Screen.Login.route,
                Screen.Signup.route,
                Screen.SignUpSuccess.route,
                Screen.ForgotPassword.route,
                Screen.EmailVerification.route,
                Screen.PasswordVerification.route,
                Screen.NewPassword.route,
                Screen.PasswordSuccess.route
            )

            if (!noTopBarRoutes.contains(route)) {
                when (route) {
                    Cards.route -> SecondaryTopAppBar(
                        canGoBack = true,
                        onBackClick = { navController.navigateUp() },
                        title = stringResource(R.string.title_cards)
                    )
                    Activities.route -> SecondaryTopAppBar(
                        canGoBack = true,
                        onBackClick = { navController.navigateUp() },
                        title = stringResource(R.string.title_activity)
                    )
                    Screen.Profile.route -> SecondaryTopAppBar(
                        canGoBack = true,
                        onBackClick = { navController.navigateUp() },
                        title = stringResource(R.string.account)
                    )
                    SeeMore.route -> SecondaryTopAppBar(
                        canGoBack = true,
                        onBackClick = { navController.navigateUp() },
                        title = stringResource(R.string.see_more)
                    )
                    else -> {
                        val canGoBack = navController.previousBackStackEntry != null

                        SecondaryTopAppBar(
                            canGoBack = canGoBack,
                            onBackClick = { navController.navigateUp() },
                            title = route?.split('_')
                                ?.joinToString(" ") { word ->
                                    word.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                                        else it.toString()
                                    }
                                } ?: ""
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            when (currentRoute(navController)){
                Home.route -> QRFab(qrScanner::startScan)
                Cards.route -> QRFab(qrScanner::startScan)
                Activities.route -> QRFab(qrScanner::startScan)
                SeeMore.route -> QRFab(qrScanner::startScan)
                else -> {}
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.systemBarsPadding()

    ) { innerPadding ->
        AppNavHost(innerPadding, modifier = Modifier, navController = navController)
    }
}

@Composable
fun ScaffoldLandscape(navController: NavHostController, qrScanner: QRScanner){
    val systemUiController = rememberSystemUiController()

    val statusBarColor = MaterialTheme.colorScheme.primary
    val systemNavColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(systemNavColor)
    }

    Scaffold2()
    { innerPadding ->
        Row()
        {
            when (currentRoute(navController)) {
                Home.route -> NavBarLandscape(navController, qrScanner)
                Cards.route -> NavBarLandscape(navController, qrScanner)
                Activities.route -> NavBarLandscape(navController, qrScanner)
                SeeMore.route -> NavBarLandscape(navController, qrScanner)
                else -> {}
            }
            LandscapeAppNavHost(innerPadding, modifier = Modifier, navController = navController)
        }
    }
}










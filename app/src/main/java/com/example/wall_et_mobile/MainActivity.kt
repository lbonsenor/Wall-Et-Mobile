package com.example.wall_et_mobile

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wall_et_mobile.ui.theme.WallEtTheme
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
        //topBar = { CustomTopAppBar(User(1, "test", "test", "Lautaro", "test", "test", "test", "test",)) },
        bottomBar = { CustomAppBar(navController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = { QRFab(qrScanner::startScan) },
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.systemBarsPadding()

    ) { innerPadding ->
        AppNavHost(innerPadding, modifier = Modifier, navController)
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
    Scaffold2(

    )
    { innerPadding ->
        Row(
            // modifier = Modifier.fillMaxSize().padding(horizontal = innerPadding.calculateRightPadding(layoutDirection = ))
        )
        {
            NavBarLandscape(navController, qrScanner)
            LandscapeAppNavHost(innerPadding, modifier = Modifier, navController)
        }
    }
}










package com.example.wall_et_mobile

import ThemeMode
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.wall_et_mobile.data.model.getScreen
import com.example.wall_et_mobile.data.preferences.LanguagePreference
import com.example.wall_et_mobile.data.preferences.ThemePreference
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.Locale
import androidx.compose.material3.Scaffold as Scaffold2

class MainActivity : ComponentActivity() {
    private lateinit var themePreference: ThemePreference
    private lateinit var languagePreference: LanguagePreference
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        themePreference = ThemePreference(applicationContext)
        languagePreference = LanguagePreference(applicationContext)
        sessionManager = (applicationContext as MyApplication).sessionManager

        val locale = Locale(languagePreference.getLanguage())
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        setContent {
            val startDestination = remember {
                if (sessionManager.loadAuthToken() != null) {
                    Home.route
                } else {
                    Screen.Login.route
                }
            }

            val (orientation, setOrientation) = remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
            val configuration = LocalConfiguration.current
            val navController = rememberNavController()
            var currentTheme = remember { mutableStateOf(themePreference.getThemeMode()) }

            LaunchedEffect(configuration) {
                snapshotFlow { configuration.orientation }
                    .collect { setOrientation(it) }
            }

            var qrScanner = QRScanner(appContext = applicationContext)


            WallEtTheme(
                darkTheme = when (currentTheme.value) {
                    ThemeMode.LIGHT -> false
                    ThemeMode.DARK -> true
                    ThemeMode.SYSTEM -> isSystemInDarkTheme()
                    else -> isSystemInDarkTheme()
                }
            ) {
                when (orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> ScaffoldPortrait(
                        navController = navController,
                        qrScanner = qrScanner,
                        startDestination = startDestination,
                        onThemeChanged = { newTheme ->
                            themePreference.setThemeMode(newTheme)
                            currentTheme.value = newTheme
                        },
                        onLanguageChanged = { newLanguage ->
                            languagePreference.setLanguage(newLanguage)
                            val newLocale = Locale(newLanguage)
                            val newConfig = resources.configuration
                            newConfig.setLocale(newLocale)
                            resources.updateConfiguration(newConfig, resources.displayMetrics)
                            recreate()
                        }
                    )
                    else -> ScaffoldLandscape(
                        navController = navController,
                        qrScanner = qrScanner,
                        startDestination = startDestination,
                        onThemeChanged = { newTheme ->
                            themePreference.setThemeMode(newTheme)
                            currentTheme.value = newTheme
                        },
                        onLanguageChanged = { newLanguage ->
                            languagePreference.setLanguage(newLanguage)
                            val newLocale = Locale(newLanguage)
                            val newConfig = resources.configuration
                            newConfig.setLocale(newLocale)
                            resources.updateConfiguration(newConfig, resources.displayMetrics)
                            recreate()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ScaffoldPortrait(
    navController: NavHostController, 
    qrScanner: QRScanner, 
    startDestination: String,
    onThemeChanged: (ThemeMode) -> Unit, 
    onLanguageChanged: (String) -> Unit
) {
    val systemUiController = rememberSystemUiController()

    val statusBarColor = MaterialTheme.colorScheme.primary
    val systemNavColor = MaterialTheme.colorScheme.primary

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    SideEffect {
        systemUiController.setStatusBarColor(statusBarColor)
        systemUiController.setNavigationBarColor(systemNavColor)
    }
    
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                Home.route,
                Screen.Login.route,
                Screen.Signup.route,
                Screen.SignUpSuccess.route,
                Screen.ForgotPassword.route,
                Screen.EmailVerification.route,
                Screen.PasswordVerification.route,
                Screen.NewPassword.route,
                Screen.PasswordSuccess.route
            )

            val navBarRoutes = listOf(
                Home.route,
                Cards.route,
                Activities.route,
                SeeMore.route
            )

            if (!noTopBarRoutes.contains(route)) {
                when (route) {
                    Cards.route -> SecondaryTopAppBar(
                        canGoBack = false,
                        onBackClick = { navController.navigateUp() },
                        title = stringResource(R.string.title_cards)
                    )
                    Activities.route -> SecondaryTopAppBar(
                        canGoBack = false,
                        onBackClick = { navController.navigateUp() },
                        title = stringResource(R.string.title_activity)
                    )
                    Screen.Profile.route -> SecondaryTopAppBar(
                        canGoBack = true,
                        onBackClick = { navController.navigateUp() },
                        title = stringResource(R.string.account)
                    )
                    SeeMore.route -> SecondaryTopAppBar(
                        canGoBack = false,
                        onBackClick = { navController.navigateUp() },
                        title = stringResource(R.string.see_more)
                    )
                    else -> {
                        val canGoBack = navController.previousBackStackEntry != null && !navBarRoutes.contains(route)

                        SecondaryTopAppBar(
                            canGoBack = canGoBack,
                            onBackClick = { navController.navigateUp() },
                            title = stringResource(getScreen(route ?: "").labelInt)
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
        AppNavHost(
            innerPadding = innerPadding, 
            modifier = Modifier, 
            navController = navController,
            startDestination = startDestination,
            onThemeChanged = onThemeChanged, 
            onLanguageChanged = onLanguageChanged,
            qrScanner = qrScanner
        )
    }
}

@Composable
fun ScaffoldLandscape(
    navController: NavHostController, 
    qrScanner: QRScanner,
    startDestination: String,
    onThemeChanged: (ThemeMode) -> Unit, 
    onLanguageChanged: (String) -> Unit
) {
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
        Row {
            when (currentRoute(navController)) {
                Home.route -> NavBarLandscape(navController, qrScanner)
                Cards.route -> NavBarLandscape(navController, qrScanner)
                Activities.route -> NavBarLandscape(navController, qrScanner)
                SeeMore.route -> NavBarLandscape(navController, qrScanner)
                else -> {}
            }
            LandscapeAppNavHost(
                innerPadding = innerPadding,
                modifier = Modifier,
                navController = navController,
                startDestination = startDestination,
                onThemeChanged = onThemeChanged,
                onLanguageChanged = onLanguageChanged,
                qrScanner = qrScanner
            )

        }
    }
}
package com.example.wall_et_mobile

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomAppBar
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wall_et_mobile.screens.Screen.*
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.ui.platform.LocalDensity

@Composable
@Preview(device = "id:pixel_5", showBackground = true, name = "LightMode")
@Preview(device = "id:pixel_5", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DarkMode"
)
fun AppBarPreview() {
    WallEtTheme {
        Scaffold (
            bottomBar = { CustomAppBar(navController = rememberNavController()) },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = { QRFab() },
            backgroundColor = MaterialTheme.colorScheme.background

        ) { innerPadding ->

        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value // Access value directly
    return navBackStackEntry?.destination?.route
}

@Composable
fun CustomAppBar(navController: NavController){
    val screens = listOf(Home, Cards, Empty, Activities, SeeMore)
    val currentRoute = currentRoute(navController)

    BottomAppBar (
        cutoutShape = CircleShape,
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .height(WindowInsets.navigationBars.getBottom(LocalDensity.current).dp)
            .clip(RoundedCornerShape(15.dp, 15.dp))
            .navigationBarsPadding()
        ) {
            screens.forEach{ screen ->
                NavigationBarItem(
                    onClick = { navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    } },
                    selected = currentRoute == screen.route,
                    colors = NavigationBarItemColors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary,

                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.onPrimary,

                        selectedIndicatorColor = MaterialTheme.colorScheme.onPrimary,

                        disabledIconColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                    ),
                    icon = {
                        if (screen.isEnabled)
                            if (screen.iconInt == -1)
                                Icon(imageVector = screen.icon, contentDescription = "App Button")
                            else
                                Icon(painter = painterResource(screen.iconInt), contentDescription = "App Button")
                           },
                    enabled = screen.isEnabled,
                    label = { if (screen.isEnabled) Text(text = stringResource(screen.labelInt), color = MaterialTheme.colorScheme.onPrimary)},
                    alwaysShowLabel = false,
                    modifier = Modifier.navigationBarsPadding()
                )
            }


    }
}

@Composable
fun QRFab(){
    FloatingActionButton(
        onClick = {},
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .size(60.dp)
    )
    {
        Icon(imageVector = ImageVector.vectorResource(R.drawable.qrcode_scan), contentDescription = "QR", tint = MaterialTheme.colorScheme.onPrimary)
    }
}
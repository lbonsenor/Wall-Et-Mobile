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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wall_et_mobile.screens.Screen.*
import com.example.wall_et_mobile.ui.theme.WallEtTheme

@Composable
@Preview(device = "id:pixel_5", showBackground = true)
@Preview(device = "id:pixel_5", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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
fun AppNavigation(){

}

@Composable
fun CustomAppBar(navController: NavController){
    val screens = listOf(Home, Cards, Empty, Activities, SeeMore)

    BottomAppBar (
        cutoutShape = CircleShape,
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .height(80.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp))

        ) {
            screens.forEach{ screen ->
                NavigationBarItem(
                    onClick = { navController.navigate(screen.route) },
                    selected = false,
                    icon = { if (screen.isEnabled) Icon(imageVector = screen.icon, contentDescription = "App Button", tint = MaterialTheme.colorScheme.onPrimary) },
                    enabled = screen.isEnabled,
                    label = { if (screen.isEnabled) Text(text = stringResource(screen.labelInt), color = MaterialTheme.colorScheme.onPrimary)},
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
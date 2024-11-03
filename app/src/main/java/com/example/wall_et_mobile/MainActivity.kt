package com.example.wall_et_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.ui.theme.WallEtTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomAppBar
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FabPosition
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WallEtTheme {
                BottomNav()

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

@Composable
@Preview(device = "id:pixel_5", showBackground = true, backgroundColor = 0xff0000)
fun GreetingPreview() {
    WallEtTheme {
        androidx.compose.material3.Scaffold (
            bottomBar = {BottomNav()}
        ) { innerPadding ->
            Text(text = "Hello", modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun CustomAppBar(){
    BottomAppBar (
        cutoutShape = CircleShape,
        contentPadding = PaddingValues(horizontal = 50.dp),
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .height(65.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp)),

        ) {
        Icon(
            Icons.Filled.Home,
            contentDescription = "Home",
            tint = MaterialTheme.colorScheme.onPrimary
        )

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

@Composable
fun BottomNav(){
    Scaffold (
        bottomBar = { CustomAppBar() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = { QRFab() }

    ) { innerPadding ->
        
    }

}




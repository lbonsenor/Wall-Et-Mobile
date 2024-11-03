package com.example.wall_et_mobile

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.ui.theme.WallEtTheme

@Composable
@Preview(device = "id:pixel_5", showBackground = true,)
@Preview(device = "id:pixel_5", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AppBarPreview() {
    WallEtTheme {
        Scaffold (
            bottomBar = { CustomAppBar() },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = { QRFab() },
            backgroundColor = MaterialTheme.colorScheme.background

        ) { innerPadding ->

        }
    }
}

@Composable
fun CustomAppBar(){
    BottomAppBar (
        cutoutShape = CircleShape,
        contentPadding = PaddingValues(horizontal = 50.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .height(65.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp))

        ) {
        Icon(
            Icons.Filled.Home,
            contentDescription = "Home",
            tint = MaterialTheme.colorScheme.onSurface
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
package com.example.wall_et_mobile.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/*
    Primary: For buttons / Bottom bar
    Secondary: For purely purple
    Tertiary: For subtitles
    Surface: For background of components

 */

private val DarkColorScheme = darkColorScheme(
    primary = Black,
    onPrimary = White,


    background = Gray,
    onBackground = White,

    secondary = Purple,
    onSecondary = White,

    onSurface = White,
    onTertiary = DarkerGray,

)

private val LightColorScheme = lightColorScheme(
    primary = Purple,
    onPrimary = White,

    background = White,
    onBackground = Black,

    secondary = Purple,
    onSecondary = White,

    onTertiary = DarkerGray

    /* Other default colors to override
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    */
)

@Composable
fun WallEtTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
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
    primary = DarkerGray,
    onPrimary = White,

    primaryContainer = Purple,
    onPrimaryContainer = White,

    background = Black,
    onBackground = White,

    secondary = Purple,
    onSecondary = White,

    onSurface = White,
    onTertiary = Gray,

    surfaceVariant = TransparentPurple,
    onSurfaceVariant = LightPurple,

    surfaceContainer = Purple,

)

private val LightColorScheme = lightColorScheme(
    primary = Purple,
    onPrimary = White,

    background = White,
    onBackground = Black,

    primaryContainer = White,
    onPrimaryContainer = Purple,

    secondary = Purple,
    onSecondary = White,

    onTertiary = Gray,

    surface = White,
    onSurface = Black,

    surfaceVariant = TransparentPurple,
    onSurfaceVariant = Purple,

    surfaceContainer = DarkPurple,

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
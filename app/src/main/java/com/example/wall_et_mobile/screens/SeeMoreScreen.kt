import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R

@Composable
fun SeeMoreScreen(
    innerPadding: PaddingValues,
    onThemeChanged: (ThemeMode) -> Unit,
    onLanguageChanged: (String) -> Unit
) {
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingsItem(
            icon = painterResource(R.drawable.android),
            title = stringResource(R.string.theme),
            onClick = { showThemeDialog = true }
        )

        SettingsItem(
            icon = painterResource(R.drawable.language),
            title = stringResource(R.string.language),
            onClick = { showLanguageDialog = true }
        )
    }

    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.theme),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ThemeOption(
                        icon = painterResource(R.drawable.light_mode),
                        text = stringResource(R.string.light_mode),
                        onClick = {
                            onThemeChanged(ThemeMode.LIGHT)
                            showThemeDialog = false
                        }
                    )
                    ThemeOption(
                        icon = painterResource(R.drawable.dark_mode),
                        text = stringResource(R.string.dark_mode),
                        onClick = {
                            onThemeChanged(ThemeMode.DARK)
                            showThemeDialog = false
                        }
                    )
                    ThemeOption(
                        icon = painterResource(R.drawable.android),
                        text = stringResource(R.string.system_default),
                        onClick = {
                            onThemeChanged(ThemeMode.SYSTEM)
                            showThemeDialog = false
                        }
                    )
                }
            },
            confirmButton = {},
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.language),
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LanguageOption(
                        text = "English",
                        onClick = {
                            onLanguageChanged("en")
                            showLanguageDialog = false
                        }
                    )
                    LanguageOption(
                        text = "Español",
                        onClick = {
                            onLanguageChanged("es")
                            showLanguageDialog = false
                        }
                    )
                }
            },
            confirmButton = {},
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun SettingsItem(
    icon: Painter,
    title: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun ThemeOption(
    icon: Painter,
    text: String,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun LanguageOption(
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(text = text)
    }
}

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}
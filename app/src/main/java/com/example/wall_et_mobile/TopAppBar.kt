package com.example.wall_et_mobile
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(user: User) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(onClick = {  }) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        title = {
            Text(user.name, style = MaterialTheme.typography.titleSmall)
        }
    )
}

@Preview
@Composable
fun PreviewTopAppBar() {
    val testUser = User(1, "test", "test", "Lauti", "test", "test", Date(), "test")
    WallEtTheme {
        CustomTopAppBar(testUser)
    }
}
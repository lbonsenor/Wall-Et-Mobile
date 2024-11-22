package com.example.wall_et_mobile
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(user: User) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(all = 10.dp)
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        Text(
            text = user.name.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontFamily = DarkerGrotesque,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        IconButton(
            onClick = {  },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            content = { Icon(Icons.Filled.Person, "") },
        )
    }
}
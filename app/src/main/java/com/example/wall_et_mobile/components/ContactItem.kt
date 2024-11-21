package com.example.wall_et_mobile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.model.User
import com.example.wall_et_mobile.ui.theme.DarkModeYellow
import com.example.wall_et_mobile.ui.theme.LightModeYellow


@Composable
fun ContactItem(user: User, favoriteUserIds: List<Int>, onFavoriteChange: (Int, Boolean) -> Unit) {
    val isFavorited by remember (favoriteUserIds){ mutableStateOf(favoriteUserIds.contains(user.id)) }

    Card(
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.person),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .border(2.dp, MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                        .padding(10.dp)
                )
                Text(
                    text = "${user.name} ${user.lastName}",
                    //modifier = Modifier.padding(start = 8.dp),
                )
            }
            IconButton(
                onClick = {
                    user.id?.toInt()?.let {
                        onFavoriteChange(
                            it,
                            !isFavorited
                        )
                    }
                } // agregarlo a la lista de favoritos
            ) {
                Icon(
                    painter = rememberVectorPainter(image = if (isFavorited) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder),
                    contentDescription = "Favorite",
                    modifier = Modifier.padding(8.dp),
                    tint = if (isSystemInDarkTheme()) {
                        DarkModeYellow
                    } else {
                        LightModeYellow
                    }
                )
            }
        }
    }

}

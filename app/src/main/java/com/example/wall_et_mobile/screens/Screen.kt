package com.example.wall_et_mobile.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.wall_et_mobile.*

sealed class Screen(
    val route: String = "",
    val labelInt: Int = 0,

    val icon: ImageVector = Icons.Outlined.Home,
    val iconInt: Int = -1,

    val isEnabled : Boolean = true
) {
    object Home : Screen(route = "home", icon = Icons.Outlined.Home, labelInt = R.string.title_home)
    object Cards : Screen(route = "cards", iconInt = R.drawable.credit_card, labelInt = R.string.title_cards)
    object Empty : Screen(isEnabled = false)
    object Activities : Screen(route = "activities", iconInt = R.drawable.history, labelInt = R.string.title_activities)
    object SeeMore: Screen(route = "see_more", icon = Icons.Outlined.Menu, labelInt = R.string.see_more)
}



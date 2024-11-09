package com.example.wall_et_mobile.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.wall_et_mobile.*

sealed class Screen(
    val route: String = "",
    val labelInt: Int = 0,
    val icon: ImageVector = Icons.Filled.Home,
    val isEnabled : Boolean = true
) {
    object Home : Screen(route = "home", icon = Icons.Filled.Home, labelInt = R.string.title_home)
    object Cards : Screen(route = "cards", icon = Icons.Filled.Home, labelInt = R.string.title_cards)
    object Empty : Screen(isEnabled = false)
    object Activities : Screen(route = "activities", icon = Icons.Filled.Home, labelInt = R.string.title_activities)
    object SeeMore: Screen(route = "see_more", icon = Icons.Filled.Menu, labelInt = R.string.see_more)
}



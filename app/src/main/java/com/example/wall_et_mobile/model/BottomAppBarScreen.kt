package com.example.wall_et_mobile.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.wall_et_mobile.*

sealed class BottomAppBarScreen(
    val route: String = "",
    val labelInt: Int = 0,

    val icon: ImageVector = Icons.Outlined.Home,
    val iconInt: Int = -1,

    val isEnabled : Boolean = true
) {
    object Home : BottomAppBarScreen(route = "home", icon = Icons.Outlined.Home, labelInt = R.string.title_home)
    object Cards : BottomAppBarScreen(route = "cards", iconInt = R.drawable.credit_card, labelInt = R.string.title_cards)
    object Empty : BottomAppBarScreen(isEnabled = false)
    object Activities : BottomAppBarScreen(route = "activities", iconInt = R.drawable.history, labelInt = R.string.title_activity)
    object SeeMore: BottomAppBarScreen(route = "see_more", icon = Icons.Outlined.Menu, labelInt = R.string.see_more)
}

sealed class Screen(
    val route: String
) {
    object Transfer : Screen(route = "transfer")
    object SelectAmount : Screen(route = "select_amount/{id}")
}


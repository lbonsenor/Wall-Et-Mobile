package com.example.wall_et_mobile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wall_et_mobile.model.Screen
import com.example.wall_et_mobile.screens.ActivitiesScreen
import com.example.wall_et_mobile.screens.CardsScreen
import com.example.wall_et_mobile.screens.HomeScreen
import com.example.wall_et_mobile.screens.HomeScreenLandscape
import com.example.wall_et_mobile.screens.transfer.SelectAmountScreen
import com.example.wall_et_mobile.screens.transfer.SelectDestinataryScreen
import com.example.wall_et_mobile.screens.transfer.SelectPaymentScreen

@Composable
fun AppNavHost(
    innerPadding: PaddingValues,
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = {
            composable(Screen.Home.route){
                HomeScreen(
                    { navigateTo(navController, Screen.Transfer.route) },
                    { navigateTo(navController, Screen.Activities.route) }
                )}
            composable(Screen.Cards.route){ CardsScreen(innerPadding) }
            composable(Screen.Activities.route){ ActivitiesScreen(innerPadding) }
            composable(Screen.SeeMore.route){}
            composable(Screen.Transfer.route) {
                SelectDestinataryScreen(
                    innerPadding
                ) { id -> navigateTo(navController, "${Screen.SelectAmount.route}/${id}") }
            }
            composable(
                route = "${Screen.SelectAmount.route}/{id}",
                arguments = listOf(
                    navArgument(name = "id"){
                        type = NavType.IntType
                    }
                )
            ){ backStackEntry ->
                SelectAmountScreen(
                    innerPadding = innerPadding,
                    id = backStackEntry.arguments?.getInt("id")!!,
                ){ id, amount -> navigateTo(navController, "${Screen.SelectPaymentMethod.route}/${id}/${amount}") }
            }
            composable(
                route = "${Screen.SelectPaymentMethod.route}/{id}/{amount}",
                arguments = listOf(
                    navArgument(name = "id"){
                        type = NavType.IntType
                    },
                    navArgument(name = "amount"){
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                SelectPaymentScreen(innerPadding, backStackEntry.arguments?.getInt("id")!!, backStackEntry.arguments?.getString("amount")!!)
            }
        }
    )
}

@Composable
fun LandscapeAppNavHost(
    innerPadding: PaddingValues,
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = {
            composable(Screen.Home.route){
                HomeScreenLandscape(
                    innerPadding,
                    { navigateTo(navController, Screen.Transfer.route) },
                    { navigateTo(navController, Screen.Activities.route) }
                )}
            composable(Screen.Cards.route){ CardsScreen(innerPadding) }
            composable(Screen.Activities.route){ ActivitiesScreen(innerPadding) }
            composable(Screen.SeeMore.route){}
            composable(Screen.Transfer.route) {
                SelectDestinataryScreen(
                    innerPadding
                ) { id -> navigateTo(navController, "${Screen.SelectAmount.route}/${id}") }
            }
            composable(
                route = "${Screen.SelectAmount.route}/{id}",
                arguments = listOf(
                    navArgument(name = "id"){
                        type = NavType.IntType
                    }
                )
            ){ backStackEntry ->
                SelectAmountScreen(
                    innerPadding = innerPadding,
                    id = backStackEntry.arguments?.getInt("id")!!,
                ){ id, amount -> navigateTo(navController, "${Screen.SelectPaymentMethod.route}/${id}/${amount}") }
            }
            composable(
                route = "${Screen.SelectPaymentMethod.route}/{id}/{amount}",
                arguments = listOf(
                    navArgument(name = "id"){
                        type = NavType.IntType
                    },
                    navArgument(name = "amount"){
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                SelectPaymentScreen(innerPadding, backStackEntry.arguments?.getInt("id")!!, backStackEntry.arguments?.getString("amount")!!)
            }
        }
    )
}

fun navigateTo(navController : NavHostController, route: String){
    navController.navigate(route){
        popUpTo(navController.graph.findStartDestination().id) {saveState = true}
        launchSingleTop = true
        restoreState = true
    }
}

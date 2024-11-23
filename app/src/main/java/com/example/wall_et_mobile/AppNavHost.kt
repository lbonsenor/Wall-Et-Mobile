package com.example.wall_et_mobile

import SeeMoreScreen
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
import com.example.wall_et_mobile.data.model.Screen
import com.example.wall_et_mobile.screens.ActivitiesScreen
import com.example.wall_et_mobile.screens.CardsScreen
import com.example.wall_et_mobile.screens.forgotPassword.ForgotPasswordScreen
import com.example.wall_et_mobile.screens.forgotPassword.NewPasswordScreen
import com.example.wall_et_mobile.screens.forgotPassword.PasswordSuccessScreen
import com.example.wall_et_mobile.screens.forgotPassword.VerificationCodeScreen
import com.example.wall_et_mobile.screens.home.HomeScreen
import com.example.wall_et_mobile.screens.home.HomeScreenLandscape
import com.example.wall_et_mobile.screens.login.LoginScreen
import com.example.wall_et_mobile.screens.profile.ProfileScreen
import com.example.wall_et_mobile.screens.signup.SignupScreen
import com.example.wall_et_mobile.screens.signup.SignupSuccessScreen
import com.example.wall_et_mobile.screens.signup.VerificationScreen
import com.example.wall_et_mobile.screens.top_up.TopUpScreen
import com.example.wall_et_mobile.screens.transfer.SelectAmountScreen
import com.example.wall_et_mobile.screens.transfer.SelectDestinataryScreen
import com.example.wall_et_mobile.screens.transfer.SelectPaymentScreen

@Composable
fun AppNavHost(
    innerPadding: PaddingValues,
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
   startDestination: String = Screen.Login.route
    //startDestination: String = Screen.Home.route    // FOR TESTING
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = {
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToHome = { navigateTo(navController, Screen.Home.route) },
                    onNavigateToForgotPassword = { navigateTo(navController, Screen.ForgotPassword.route) },
                    onNavigateToSignUp = { navigateTo(navController, Screen.Signup.route) }
                )
            }
            composable(Screen.Signup.route) {
                SignupScreen(
                    onNavigateToLogin = { navigateTo(navController, Screen.Login.route) },
                    onNavigateUp = { navController.navigateUp() },
                    onNavigateToVerification = { navigateTo(navController, Screen.Verification.route) }
                )
            }
            composable(Screen.SignUpSuccess.route) {
                SignupSuccessScreen(
                    onContinue = { navigateTo(navController, Screen.Login.route) }
                )
            }
            composable(Screen.Verification.route) {
                VerificationScreen(
                     { navigateTo(navController, Screen.SignUpSuccess.route) },
                )
            }
            composable(Screen.ForgotPassword.route){ ForgotPasswordScreen(
                onNavigateToLogin = { navigateTo(navController, Screen.Login.route) },
                onNavigateToVerification = { navigateTo(navController, Screen.VerificationCode.route) }
            ) 
        }
            composable(Screen.NewPassword.route) {
                NewPasswordScreen(
                    { navigateTo(navController, Screen.PasswordSuccess.route) },
                )
            }
            composable(Screen.PasswordSuccess.route) {
                PasswordSuccessScreen {
                    navigateTo(navController, Screen.Login.route)
                }
            }
            composable(Screen.VerificationCode.route) {
                VerificationCodeScreen(
                    onNavigateToSetNewPassword = { navigateTo(navController, Screen.NewPassword.route) }
                )
            }
            composable(Screen.Home.route){
                HomeScreen(
                    { navigateTo(navController, Screen.Transfer.route) },
                    { navigateTo(navController, Screen.Activities.route) },
                    { navigateTo(navController, Screen.Login.route) },
                    { navigateTo(navController, Screen.TopUp.route) },
                    { navigateTo(navController, Screen.Profile.route) }
                )
            }
            composable(Screen.Cards.route){ CardsScreen(innerPadding) }
            composable(Screen.Activities.route){ ActivitiesScreen(innerPadding) }
            composable(Screen.SeeMore.route) {
                SeeMoreScreen(
                    innerPadding = innerPadding,
                    onThemeChanged = { /* TODO: Implement theme change */ },
                    onLanguageChanged = { /* TODO: Implement language change */ }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToLogin = { navigateToLogin(navController) }
                )
            }
            composable(Screen.Transfer.route) {
                SelectDestinataryScreen(
                    innerPadding = innerPadding,
                    onNavigateToSelectAmount = { email -> navigateTo(navController, "${Screen.SelectAmount.route}/${email}") }
                )
            }
            composable(
                route = "${Screen.SelectAmount.route}/{email}",
                arguments = listOf(
                    navArgument(name = "email"){
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                SelectAmountScreen(
                    innerPadding = innerPadding,
                    email = backStackEntry.arguments?.getString("email")!!,
                    onNavigateToSelectPayment = { email, amount ->
                        navigateTo(navController, "${Screen.SelectPaymentMethod.route}/${email}/${amount}")
                    },
                )
            }
            composable(
                route = "${Screen.SelectPaymentMethod.route}/{email}/{amount}",
                arguments = listOf(
                    navArgument(name = "email"){
                        type = NavType.StringType
                    },
                    navArgument(name = "amount"){
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                SelectPaymentScreen(
                    innerPadding = innerPadding,
                    onPaymentComplete = { navigateTo(navController, Screen.Home.route) },
                    email = backStackEntry.arguments?.getString("email")!!,
                    amount = backStackEntry.arguments?.getString("amount")!!,
                    onChangeDestination = { navigateTo(navController, Screen.Transfer.route) },
                    onEditAmount = { navigateTo(navController, "${Screen.SelectAmount.route}/${backStackEntry.arguments?.getString("email")}") }
                )
            }
            composable(route = Screen.TopUp.route){ TopUpScreen(innerPadding) }
        }
    )
}

@Composable
fun LandscapeAppNavHost(
    innerPadding: PaddingValues,
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = {
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToHome = { navigateTo(navController, Screen.Home.route) },
                    onNavigateToForgotPassword = { navigateTo(navController, Screen.ForgotPassword.route) },
                    onNavigateToSignUp = { navigateTo(navController, Screen.Signup.route) }
                )
            }
            composable(Screen.Signup.route) {
                SignupScreen(
                    onNavigateToLogin = { navigateTo(navController, Screen.Login.route) },
                    onNavigateUp = { navController.navigateUp() },
                    onNavigateToVerification = { navigateTo(navController, Screen.Verification.route) }
                )
            }
            composable(Screen.SignUpSuccess.route) {
                SignupSuccessScreen(
                    onContinue = { navigateTo(navController, Screen.Login.route) }
                )
            }
            composable(Screen.Verification.route) {
                VerificationScreen(
                    { navigateTo(navController, Screen.SignUpSuccess.route) },
                )
            }
            composable(Screen.ForgotPassword.route){ ForgotPasswordScreen(
                onNavigateToLogin = { navigateTo(navController, Screen.Login.route) },
                onNavigateToVerification = { navigateTo(navController, Screen.Verification.route) }
            ) }
            composable(Screen.NewPassword.route) {
                NewPasswordScreen(
                    { navigateTo(navController, Screen.PasswordSuccess.route) },
                )
            }
            composable(Screen.PasswordSuccess.route) {
                PasswordSuccessScreen {
                    navigateTo(navController, Screen.Login.route)
                }
            }
            composable(Screen.VerificationCode.route) {
                VerificationCodeScreen(
                    onNavigateToSetNewPassword = { navigateTo(navController, Screen.NewPassword.route) }
                )
            }
            composable(Screen.Home.route){
                HomeScreenLandscape(
                    innerPadding,
                    { navigateTo(navController, Screen.Transfer.route) },
                    { navigateTo(navController, Screen.Activities.route) },
                    { navigateTo(navController, Screen.TopUp.route) },
                    { navigateTo(navController, Screen.Profile.route) }
                )}
            composable(Screen.Cards.route){ CardsScreen(innerPadding) }
            composable(Screen.Activities.route){ ActivitiesScreen(innerPadding) }
            composable(Screen.SeeMore.route) {
                SeeMoreScreen(
                    innerPadding = innerPadding,
                    onThemeChanged = { /* TODO: Implement theme change */ },
                    onLanguageChanged = { /* TODO: Implement language change */ }
                )
            }
            composable(Screen.Transfer.route) {
                SelectDestinataryScreen(
                    innerPadding = innerPadding,
                    onNavigateToSelectAmount = { email -> navigateTo(navController, "${Screen.SelectAmount.route}/${email}") }
                )
            }
            composable(
                route = "${Screen.SelectAmount.route}/{email}",
                arguments = listOf(
                    navArgument(name = "email"){
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                SelectAmountScreen(
                    innerPadding = innerPadding,
                    email = backStackEntry.arguments?.getString("email")!!,
                    onNavigateToSelectPayment = { email, amount ->
                        navigateTo(navController, "${Screen.SelectPaymentMethod.route}/${email}/${amount}")
                    },
                )
            }
            composable(
                route = "${Screen.SelectPaymentMethod.route}/{email}/{amount}",
                arguments = listOf(
                    navArgument(name = "email"){
                        type = NavType.StringType
                    },
                    navArgument(name = "amount"){
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                SelectPaymentScreen(
                    innerPadding = innerPadding,
                    onPaymentComplete = { navigateTo(navController, Screen.Home.route) },
                    email = backStackEntry.arguments?.getString("email")!!,
                    amount = backStackEntry.arguments?.getString("amount")!!,
                    onChangeDestination = { navigateTo(navController, Screen.Transfer.route) },
                    onEditAmount = { navigateTo(navController, "${Screen.SelectAmount.route}/${backStackEntry.arguments?.getString("email")}") }
                )
            }
            composable(route = Screen.TopUp.route){ TopUpScreen(innerPadding) }
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

fun navigateToLogin(navController: NavHostController) {
    navController.navigate(Screen.Login.route) {
        popUpTo(0)
        launchSingleTop = true
    }
}
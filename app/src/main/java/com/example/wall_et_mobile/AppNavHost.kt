package com.example.wall_et_mobile

import SeeMoreScreen
import SelectAmountScreen
import SelectAmountScreenLandscape
import ThemeMode
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wall_et_mobile.data.model.Screen
import com.example.wall_et_mobile.screens.activities.ActivitiesScreen
import com.example.wall_et_mobile.screens.cards.CardsScreen
import com.example.wall_et_mobile.screens.forgotPassword.ForgotPasswordScreen
import com.example.wall_et_mobile.screens.forgotPassword.NewPasswordScreen
import com.example.wall_et_mobile.screens.forgotPassword.PasswordSuccessScreen
import com.example.wall_et_mobile.screens.forgotPassword.PasswordVerificationScreen
import com.example.wall_et_mobile.screens.home.HomeScreen
import com.example.wall_et_mobile.screens.home.HomeScreenLandscape
import com.example.wall_et_mobile.screens.login.LoginScreen
import com.example.wall_et_mobile.screens.profile.ProfileScreen
import com.example.wall_et_mobile.screens.signup.EmailVerificationScreen
import com.example.wall_et_mobile.screens.signup.SignupScreen
import com.example.wall_et_mobile.screens.signup.SignupSuccessScreen
import com.example.wall_et_mobile.screens.top_up.TopUpScreen
import com.example.wall_et_mobile.screens.transfer.ConfirmLinkPaymentScreen
import com.example.wall_et_mobile.screens.transfer.ConfirmLinkPaymentScreenLandscape
import com.example.wall_et_mobile.screens.transfer.ConfirmPaymentScreen
import com.example.wall_et_mobile.screens.transfer.ConfirmPaymentScreenLandscape
import com.example.wall_et_mobile.screens.transfer.SelectDestinataryScreen
import com.example.wall_et_mobile.screens.transfer.SelectDestinataryScreenLandscape

@Composable
fun AppNavHost(
    innerPadding: PaddingValues,
    modifier: Modifier,
    navController: NavHostController,
    onThemeChanged: (ThemeMode) -> Unit,
    onLanguageChanged: (String) -> Unit,
    startDestination: String,
    qrScanner: QRScanner
){
    var qrResults = qrScanner.barCodeResults.collectAsStateWithLifecycle()

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
                    onNavigateToVerification = { navigateTo(navController, Screen.EmailVerification.route) }
                )
            }
            composable(Screen.SignUpSuccess.route) {
                SignupSuccessScreen(
                    onContinue = { navigateTo(navController, Screen.Login.route) }
                )
            }
            composable(Screen.EmailVerification.route) {
                EmailVerificationScreen(
                    { navigateTo(navController, Screen.SignUpSuccess.route) }
                )
            }
            composable(Screen.ForgotPassword.route){ ForgotPasswordScreen(
                onNavigateToLogin = { navigateTo(navController, Screen.Login.route) },
                onNavigateToVerification = { navigateTo(navController, Screen.PasswordVerification.route) }
            )
        }
            composable(Screen.NewPassword.route) {
                NewPasswordScreen { navigateTo(navController, Screen.PasswordSuccess.route) }
            }
            composable(Screen.PasswordSuccess.route) {
                PasswordSuccessScreen {
                    navigateTo(navController, Screen.Login.route)
                }
            }
            composable(Screen.PasswordVerification.route) {
                PasswordVerificationScreen(
                    onNavigateToSetNewPassword = { navigateTo(navController, Screen.NewPassword.route) }
                )
            }
            composable(Screen.Home.route){
                HomeScreen(
                    onNavigateToTransfer = { navigateTo(navController, Screen.Transfer.route) },
                    onNavigateToActivity = { navigateTo(navController, Screen.Activities.route) },
                    onNavigateToTopUp = { navigateTo(navController, Screen.TopUp.route) },
                    onNavigateToProfile = { navigateTo(navController, Screen.Profile.route) }
                )
            }
            composable(Screen.Cards.route){ CardsScreen(innerPadding) }
            composable(Screen.Activities.route){ ActivitiesScreen(innerPadding) }
            composable(Screen.SeeMore.route) {
                SeeMoreScreen(
                    innerPadding = innerPadding,
                    onThemeChanged = onThemeChanged,
                    onLanguageChanged = onLanguageChanged
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToLogin = { navigateToLogin(navController) },
                )
            }
            composable(Screen.Transfer.route) {
                SelectDestinataryScreen(
                    innerPadding = innerPadding,
                    onNavigateToSelectAmount = { email -> navigateToNew(navController, "${Screen.SelectAmount.route}/${email}") }
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
                    onNavigateToSelectPayment = { email, amount, paymentType, cardId, cardDigits ->
                        navigateToNew(navController, "${Screen.ConfirmPaymentMethod.route}/${email}/${amount}/${paymentType}/${cardId}/${cardDigits}")
                    },
                    onChangeDestination = {
                        navigateToNew(navController, Screen.Transfer.route)
                    }
                )
            }
            composable(
                route = "${Screen.ConfirmPaymentMethod.route}/{email}/{amount}/{paymentType}/{cardId}/{cardDigits}",
                arguments = listOf(
                    navArgument(name = "email"){
                        type = NavType.StringType
                    },
                    navArgument(name = "amount"){
                        type = NavType.StringType
                    },
                    navArgument(name = "paymentType"){
                        type = NavType.StringType
                    },
                    navArgument(name = "cardId") {
                        type = NavType.IntType
                    },
                    navArgument(name = "cardDigits") {
                        type = NavType.StringType
                    }
                )
            ){ backStackEntry ->
                ConfirmPaymentScreen(
                    innerPadding = innerPadding,
                    onPaymentComplete = { navigateToNew(navController, Screen.Home.route) },
                    email = backStackEntry.arguments?.getString("email")!!,
                    amount = backStackEntry.arguments?.getString("amount")!!,
                    paymentType = backStackEntry.arguments?.getString("paymentType")!!,
                    cardId = backStackEntry.arguments?.getInt("cardId"),
                    onChangeDestination = { navigateToNew(navController, Screen.Transfer.route) },
                    onEditAmount = {
                        navigateToNew(
                            navController,
                            "${Screen.SelectAmount.route}/${backStackEntry.arguments?.getString("email")}"
                        )
                    },
                    cardDigits = backStackEntry.arguments?.getString("cardDigits"),
                )
            }
            composable(route = Screen.TopUp.route){
                TopUpScreen(
                    innerPadding = innerPadding,
                    onConfirm = { navigateToNew(navController, Screen.Home.route) }
                ) }
            composable(route = "${Screen.ConfirmLinkPayment.route}/{link}",
                arguments = listOf(
                    navArgument(name = "link"){
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                ConfirmLinkPaymentScreen(
                    innerPadding = innerPadding,
                    onPaymentComplete = {},
                    link = backStackEntry.arguments?.getString("link")!!,
                )
            }
        }
    )
    LaunchedEffect(qrResults.value) {
        if (qrResults.value != null) {
            navigateTo(navController, "${Screen.ConfirmLinkPayment.route}/${qrResults.value}")
        }
    }
}

@Composable
fun LandscapeAppNavHost(
    innerPadding: PaddingValues,
    modifier: Modifier,
    navController: NavHostController,
    onThemeChanged: (ThemeMode) -> Unit,
    onLanguageChanged: (String) -> Unit,
    startDestination: String,
    qrScanner: QRScanner
){
    var qrResults = qrScanner.barCodeResults.collectAsStateWithLifecycle()
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
                    onNavigateToVerification = { navigateTo(navController, Screen.EmailVerification.route) }
                )
            }
            composable(Screen.SignUpSuccess.route) {
                SignupSuccessScreen(
                    onContinue = { navigateTo(navController, Screen.Login.route) }
                )
            }
            composable(Screen.EmailVerification.route) {
                EmailVerificationScreen(
                    onNavigateToSuccess = { navigateTo(navController, Screen.SignUpSuccess.route) }
                )
            }
            composable(Screen.ForgotPassword.route){ ForgotPasswordScreen(
                onNavigateToLogin = { navigateTo(navController, Screen.Login.route) },
                onNavigateToVerification = { navigateTo(navController, Screen.PasswordVerification.route) }
            ) }
            composable(Screen.NewPassword.route) {
                NewPasswordScreen {
                    navigateTo(navController, Screen.PasswordSuccess.route)
                }
            }
            composable(Screen.PasswordSuccess.route) {
                PasswordSuccessScreen {
                    navigateTo(navController, Screen.Login.route)
                }
            }
            composable(Screen.PasswordVerification.route) {
                PasswordVerificationScreen(
                    onNavigateToSetNewPassword = { navigateTo(navController, Screen.NewPassword.route) }
                )
            }
            composable(Screen.Home.route){
                HomeScreenLandscape(
                    innerPadding,
                    { navigateTo(navController, Screen.Transfer.route) },
                    { navigateToNew(navController, Screen.Activities.route) },
                    { navigateToNew(navController, Screen.TopUp.route) },
                    { navigateTo(navController, Screen.Profile.route) }
                )}
            composable(Screen.Cards.route){ CardsScreen(innerPadding) }
            composable(Screen.Activities.route){ ActivitiesScreen(innerPadding) }
            composable(Screen.SeeMore.route) {
                SeeMoreScreen(
                    innerPadding = innerPadding,
                    onThemeChanged = onThemeChanged,
                    onLanguageChanged = onLanguageChanged
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToLogin = { navigateToLogin(navController) }
                )
            }
            composable(Screen.Transfer.route) {
                SelectDestinataryScreenLandscape(
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
                SelectAmountScreenLandscape(
                    innerPadding = innerPadding,
                    email = backStackEntry.arguments?.getString("email")!!,
                    onNavigateToSelectPayment = { email, amount, paymentType, cardId ->
                        navigateTo(navController, "${Screen.ConfirmPaymentMethod.route}/${email}/${amount}/${paymentType}/${cardId}")
                    },
                    onChangeDestination = {
                        navigateTo(navController, Screen.Transfer.route)
                    }
                )
            }
            composable(
                route = "${Screen.ConfirmPaymentMethod.route}/{email}/{amount}/{paymentType}/{cardId}",
                arguments = listOf(
                    navArgument(name = "email"){
                        type = NavType.StringType
                    },
                    navArgument(name = "amount"){
                        type = NavType.StringType
                    },
                    navArgument(name = "paymentType"){
                        type = NavType.StringType
                    },
                    navArgument(name = "cardId") {
                        type = NavType.IntType
                    }
                )
            ){ backStackEntry ->
                ConfirmPaymentScreenLandscape(
                    innerPadding = innerPadding,
                    onPaymentComplete = { navigateTo(navController, Screen.Home.route) },
                    email = backStackEntry.arguments?.getString("email")!!,
                    amount = backStackEntry.arguments?.getString("amount")!!,
                    paymentType = backStackEntry.arguments?.getString("paymentType")!!,
                    cardId = backStackEntry.arguments?.getInt("cardId"),
                    cardDigits = backStackEntry.arguments?.getString("cardDigits"),
                    onChangeDestination = { navigateTo(navController, Screen.Transfer.route) },
                    onEditAmount = { navigateTo(navController, "${Screen.SelectAmount.route}/${backStackEntry.arguments?.getString("email")}") }
                )
            }
            composable(route = Screen.TopUp.route){ TopUpScreen(innerPadding) }
            composable(route = "${Screen.ConfirmLinkPayment.route}/{link}",
                arguments = listOf(
                    navArgument(name = "link"){
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                ConfirmLinkPaymentScreenLandscape(
                    innerPadding = innerPadding,
                    onPaymentComplete = {},
                    link = backStackEntry.arguments?.getString("link")!!,
                )
            }
        }
    )
    LaunchedEffect(qrResults.value) {
        if (qrResults.value != null) {
            navigateTo(navController, "${Screen.ConfirmLinkPayment.route}/${qrResults.value}")
        }
    }
}

fun navigateTo(navController : NavHostController, route: String){
    navController.navigate(route){
        popUpTo(navController.graph.findStartDestination().id)
        {saveState = true}
        launchSingleTop = true
        restoreState = true
    }
}
fun navigateToNew(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = false
        }
        launchSingleTop = true
        restoreState = false
    }
}

fun navigateToLogin(navController: NavHostController) {
    navController.navigate(Screen.Login.route) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
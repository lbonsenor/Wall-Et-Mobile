package com.example.wall_et_mobile.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.model.Screen.Activities
import com.example.wall_et_mobile.data.model.Screen.Cards
import com.example.wall_et_mobile.data.model.Screen.ConfirmPaymentMethod
import com.example.wall_et_mobile.data.model.Screen.EmailVerification
import com.example.wall_et_mobile.data.model.Screen.ForgotPassword
import com.example.wall_et_mobile.data.model.Screen.Home
import com.example.wall_et_mobile.data.model.Screen.Login
import com.example.wall_et_mobile.data.model.Screen.NewPassword
import com.example.wall_et_mobile.data.model.Screen.PasswordSuccess
import com.example.wall_et_mobile.data.model.Screen.PasswordVerification
import com.example.wall_et_mobile.data.model.Screen.Profile
import com.example.wall_et_mobile.data.model.Screen.SeeMore
import com.example.wall_et_mobile.data.model.Screen.SelectAmount
import com.example.wall_et_mobile.data.model.Screen.SignUpSuccess
import com.example.wall_et_mobile.data.model.Screen.Signup
import com.example.wall_et_mobile.data.model.Screen.TopUp
import com.example.wall_et_mobile.data.model.Screen.Transfer

sealed class Screen(
    val route: String = "",
    val labelInt: Int = 0,

    val icon: ImageVector = Icons.Outlined.Home,
    val iconInt: Int = -1,

    val isEnabled : Boolean = true
) {
    object Login : Screen(route = "login")
    object Signup : Screen(route = "signup")
    object SignUpSuccess : Screen(route = "signup_success")
    object ForgotPassword : Screen(route = "forgot_password")
    object PasswordVerification : Screen(route = "password_verification") // for forgot pass
    object EmailVerification : Screen(route = "email_verification") // for login verification
    object NewPassword : Screen(route = "new_password")
    object PasswordSuccess : Screen(route = "password_success")
    object Home : Screen(route = "home", icon = Icons.Outlined.Home, labelInt = R.string.title_home)
    object Cards : Screen(route = "cards", iconInt = R.drawable.credit_card, labelInt = R.string.title_cards)
    object Empty : Screen(isEnabled = false)
    object Activities : Screen(route = "activities", iconInt = R.drawable.history, labelInt = R.string.title_activity)
    object SeeMore: Screen(route = "see_more", icon = Icons.Outlined.Menu, labelInt = R.string.see_more)
    object Transfer : Screen(route = "transfer", labelInt = R.string.title_transfer)
    object SelectAmount : Screen(route = "select_amount", labelInt = R.string.title_transfer)
    object ConfirmPaymentMethod : Screen(route = "confirm_payment_method", labelInt = R.string.title_transfer)
    object TopUp : Screen(route = "top_up", labelInt = R.string.title_add_fund)
    object Profile : Screen(route = "profile", labelInt = R.string.account)
}

fun getScreen(route: String) : Screen {
    when (route){
        "login" -> return Login
        "signup" -> return Signup
        "signup_success" -> return SignUpSuccess
        "forgot_password" -> return ForgotPassword
        "password_verification" -> return PasswordVerification
        "email_verification" -> return EmailVerification
        "new_password" -> return NewPassword
        "password_success" -> return PasswordSuccess
        "home" -> return Home
        "cards" -> return Cards
        "activities" -> return Activities
        "see_more" -> return SeeMore
        "transfer" -> return Transfer
        "select_amount" -> return SelectAmount
        "confirm_payment_method" -> return ConfirmPaymentMethod
        "top_up" -> return TopUp
        "profile" -> return Profile
        else -> return Home
    }
}


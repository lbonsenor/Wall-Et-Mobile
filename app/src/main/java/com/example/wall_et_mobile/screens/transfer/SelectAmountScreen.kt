package com.example.wall_et_mobile.screens.transfer


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wall_et_mobile.components.TransferProgress
import com.example.wall_et_mobile.data.mock.MockContacts
import com.example.wall_et_mobile.model.User
import com.example.wall_et_mobile.ui.theme.White

@Composable
fun SelectAmountScreen(innerPadding : PaddingValues, navController: NavController, id: String?) {

    val user : User? = if (id?.matches("\\d+".toRegex()) == true){
        MockContacts.getUserByCVU(id)
    } else {
        MockContacts.getUserByAlias(id ?: "")
    }

    if (user == null) { navController.popBackStack() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TransferProgress(1)
        Text(text = "${user?.name}", color = White)

    }

}
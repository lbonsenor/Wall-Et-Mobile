package com.example.wall_et_mobile.screens.transfer


import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.TransferProgress
import com.example.wall_et_mobile.data.mock.MockContacts
import com.example.wall_et_mobile.model.User
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque

@Composable
fun SelectAmountScreen(innerPadding : PaddingValues, navController: NavController, id: Int?) {

    val user : User = MockContacts.sampleContacts[id!!]
    var amount by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TransferProgress(1)
        ContactCard(user)
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = DarkerGrotesque,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
            ),
        )
    }

}

@Composable
fun ContactCard(user: User) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(25.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
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
                fontWeight = FontWeight.SemiBold
            )
        }
        OutlinedButton (
            onClick = { },
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.onSecondary,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = stringResource(R.string.change))
        }
    }
}
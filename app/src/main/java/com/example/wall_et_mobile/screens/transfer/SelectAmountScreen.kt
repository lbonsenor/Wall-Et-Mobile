

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.SelectedOption
import com.example.wall_et_mobile.components.TransferCardSlider
import com.example.wall_et_mobile.components.TransferProgress
import com.example.wall_et_mobile.data.model.PaymentType
import com.example.wall_et_mobile.screens.transfer.TransferViewModel
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SelectAmountScreen(
    innerPadding : PaddingValues,
    email: String,
    onChangeDestination: () -> Unit,
    onNavigateToSelectPayment : (String, String, String, Int?) -> Unit, // email, amount, paymentType, cardId
    viewModel: TransferViewModel = viewModel(factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
)
{
    val uiState = viewModel.uiState

    var amount by remember { mutableStateOf("") }
    var cents by remember { mutableStateOf("00") }
    var selectedPaymentMethod by remember { mutableStateOf<SelectedOption?>(null) }

    var isEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getWallet()
        viewModel.getBalance()
        viewModel.getCards()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TransferProgress(1)
        ContactCard(email,
            Modifier.fillMaxWidth(),
            onClick = onChangeDestination)

        AmountTextField(amount, cents, { amount = it }, { cents = it })

        Box(
            contentAlignment = Alignment.Center,
        ) {

            TransferCardSlider(
                cards = uiState.cards,
                balance = uiState.balance ?: 0.0, // this is literally so wrongviewModel.getBalance() ?: 0.0, // this is literally so wrong
                selectedOption = selectedPaymentMethod,
                onSelectionChange = { selectedPaymentMethod = it }
            )
//            paymentMethod = when (selectedPaymentMethod) {
//                is SelectedOption.Wallet -> PaymentType.BALANCE.toString()
//                is SelectedOption.Card -> {
//                    cardId = SelectedOption
//                    PaymentType.CARD.toString()
//                }
//                else -> throw IllegalStateException("Unexpected payment method: $selectedPaymentMethod")
//            }
        }

        Button(
            enabled = amount.isNotEmpty() && selectedPaymentMethod != null && isEnabled,
            onClick = {
                var cardId: Int? = 0
                var paymentType: String
                when (selectedPaymentMethod) {
                    is SelectedOption.WalletOption ->
                    {
                        paymentType = PaymentType.BALANCE.toString()

                    }
                    is SelectedOption.CardOption -> {
                        cardId = (selectedPaymentMethod as SelectedOption.CardOption).card.cardId
                        paymentType = PaymentType.CARD.toString()
                    }
                    is SelectedOption.LinkOption -> {
                        paymentType = PaymentType.LINK.toString()
                    }
                    null -> return@Button
                }

                onNavigateToSelectPayment(email, amount, paymentType, cardId)
            },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = Color.Gray.copy(0.7f),
                disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(0.5f)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(stringResource(R.string.continue_button))
        }

        if (selectedPaymentMethod is SelectedOption.WalletOption && amount.isNotEmpty()) {
            if (uiState.balance!! < amount.toDouble() ) {
                isEnabled = false
                Text(
                    text = stringResource(R.string.insufficient_funds),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                isEnabled = true
            }
        }
    }

}

class AmountTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = text.text
        out = formatAmount(text.text)
        return TransformedText(
            AnnotatedString(out),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return when (offset) {
                        0,1,2,3 -> offset
                        4,5,6 -> offset + 1
                        else -> offset + 2
                    }
                }
                override fun transformedToOriginal(offset: Int): Int {
                    if (text.text.isEmpty()) return offset
                    return when (offset) {
                        0,1,2,3 -> offset
                        4,5,6 -> offset - 1
                        else -> offset - 2
                    }
                }
            }
        )
    }
}

@Composable
fun ContactCard(email: String, modifier : Modifier, onClick : () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
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
            text = email,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Clip
        )

        FilledTonalButton (
            onClick = onClick,
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary.copy(0.8f),
            ),

            //border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = stringResource(R.string.change), overflow = TextOverflow.Visible)
        }
    }
}



@Composable
fun AmountTextField(whole: String, cents: String, setWhole: (String) -> Unit, setCents: (String) -> Unit) {
    Row (verticalAlignment = Alignment.Bottom){
        TextField(
            supportingText = {Text("${stringResource(R.string.max_amount)} $6,000,000.00")},
            value = whole,
            onValueChange = {
                if (it.isEmpty()) setWhole(it)
                else if (it.isDigitsOnly()) {
                    if (it.length > 1 && it[0] == '0') setWhole(it.trimStart('0').ifEmpty { "0" })
                    else if (it.toDouble() <= 6000000) setWhole(it)
                    else if (it.toDouble() >= 6000000) setCents("00")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = DarkerGrotesque,
                fontWeight = FontWeight.SemiBold,
                fontSize = 45.sp,
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,  // bottom line color when focused
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), // bottom line color when not focused
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.Transparent,  // background color
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            prefix = {
                Text(
                    text = "$ ",
                    style = TextStyle(
                        fontFamily = DarkerGrotesque,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 45.sp))
            },
            singleLine = true,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .weight(0.75f),
            visualTransformation = AmountTransformation(),
        )
        TextField(
            supportingText = {Text(" ")},
            value = cents,
            onValueChange = {
                if (it.isDigitsOnly() && it.length <= 2) setCents(it)
                if (whole == "6000000") setCents("00")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = DarkerGrotesque,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,  // bottom line color when focused
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), // bottom line color when not focused
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.Transparent,  // background color
                unfocusedContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            prefix = {
                Text(
                    text = ". ",
                    style = TextStyle(
                        fontFamily = DarkerGrotesque,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp))
            },
            singleLine = true,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .weight(0.25f),
        )
    }

}

fun formatAmount(amount: String): String {
    if (amount.isEmpty()) return ""
    return try {
        val number = amount.toDoubleOrNull() ?: return ""
        if (number > 6000000) return "6,000,000"
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        formatter.minimumFractionDigits = 0
        formatter.maximumFractionDigits = 0
        formatter.format(number)
    } catch (e: Exception) {
        Log.e("Error on formatting string", e.toString())
        ""
    }

}

@Composable
fun SelectAmountScreenLandscape(
    innerPadding : PaddingValues,
    email: String,
    onChangeDestination: () -> Unit,
    onNavigateToSelectPayment : (String, String, String, Int?) -> Unit, // email, amount, paymentType, cardId
    viewModel: TransferViewModel = viewModel(factory = TransferViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
)
{
    val uiState = viewModel.uiState

    var amount by remember { mutableStateOf("") }
    var cents by remember { mutableStateOf("00") }
    var selectedPaymentMethod by remember { mutableStateOf<SelectedOption?>(null) }

    var isEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getWallet()
        viewModel.getBalance()
        viewModel.getCards()
    }

    Row {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .weight(0.4f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TransferProgress(1)
            ContactCard(email,
                Modifier.fillMaxWidth(),
                onClick = onChangeDestination)
            Button(
                enabled = amount.isNotEmpty() && selectedPaymentMethod != null && isEnabled,
                onClick = {
                    var cardId: Int? = 0
                    var paymentType: String
                    when (selectedPaymentMethod) {
                        is SelectedOption.WalletOption ->
                        {
                            paymentType = PaymentType.BALANCE.toString()

                        }
                        is SelectedOption.CardOption -> {
                            cardId = (selectedPaymentMethod as SelectedOption.CardOption).card.cardId
                            paymentType = PaymentType.CARD.toString()
                        }
                        is SelectedOption.LinkOption -> {
                            paymentType = PaymentType.LINK.toString()
                        }
                        null -> return@Button
                    }

                    onNavigateToSelectPayment(email, amount, paymentType, cardId)
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    disabledContainerColor = Color.Gray.copy(0.7f),
                    disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
            ) {
                Text(stringResource(R.string.continue_button))
            }
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .weight(0.4f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AmountTextField(amount, cents, { amount = it }, { cents = it })

            Box(
                contentAlignment = Alignment.Center,
            ) {

                TransferCardSlider(
                    cards = uiState.cards,
                    balance = uiState.balance ?: 0.0, // this is literally so wrongviewModel.getBalance() ?: 0.0, // this is literally so wrong
                    selectedOption = selectedPaymentMethod,
                    onSelectionChange = { selectedPaymentMethod = it }
                )


                if (selectedPaymentMethod is SelectedOption.WalletOption && amount.isNotEmpty()) {
                    if (uiState.balance!! < amount.toDouble() ) {
                        isEnabled = false
                        Text(
                            text = stringResource(R.string.insufficient_funds),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        isEnabled = true
                    }
                }
            }
        }
    }




}
package com.example.wall_et_mobile.screens.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.TransferProgress
import com.example.wall_et_mobile.data.mock.MockContacts
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque
import kotlin.math.roundToInt

// should change name to last section screen or smth
@Composable
fun SelectPaymentScreen(innerPadding : PaddingValues, id: Int, amount: String){
    var note by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
    ) {

        SuccessDialog(
            visible = showSuccess,
            message = stringResource(R.string.payment_success),
            onDismiss = { showSuccess = false }
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            TransferProgress(2)

            Section(title = stringResource(R.string.transfer_to)) {
                ContactCard(MockContacts.sampleContacts[id], Modifier.fillMaxWidth(), onClick = {})

            }

            Section(title = stringResource(R.string.transfer_amount)) {
                AmountDisplay(amount)
            }

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                placeholder = { Text(stringResource(R.string.add_note)) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    focusedBorderColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
        }
        SwipeToSendButton(
            onSwipeComplete = { showSuccess = true },
            isCompleted = showSuccess,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .fillMaxWidth()
        )
    }
}


@Composable
fun SwipeToSendButton(
    onSwipeComplete: () -> Unit,
    isCompleted: Boolean = false,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var width by remember { mutableStateOf(0) }
    val density = LocalDensity.current


    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            offsetX = width - with(density) { 56.dp.toPx() }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .onSizeChanged { width = it.width }
    ) {
        Text(
            text = stringResource(R.string.swipe_to_send),
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .size(60.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.onSecondary)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (offsetX > width * 0.5f) {
                                offsetX = width - with(density) { 56.dp.toPx() }
                                onSwipeComplete()
                            } else {
                                offsetX = 0f
                            }
                        },
                        onDragCancel = {
                            if (!isCompleted) offsetX = 0f
                        },
                        onHorizontalDrag  = { change, dragAmount ->
                            if (!isCompleted) {
                                change.consume()
                                offsetX = (offsetX + dragAmount).coerceIn(0f, width - with(density) { 56.dp.toPx() })
                            }
                        }
                    )
                }
        ) {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = stringResource(R.string.swipe),
                modifier = Modifier.align(Alignment.Center),
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        // Double arrow indicator
        Text(
            text = "⟫",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
        )
    }
}


@Composable
private fun AmountDisplay(amount: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Text(
                text = "$",
                style = TextStyle(
                    fontFamily = DarkerGrotesque,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp
                ),
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = amount.ifEmpty { "0.00" },
                style = TextStyle(
                    fontFamily = DarkerGrotesque,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 48.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        FilledTonalButton(
            onClick = { },
            shape = CircleShape
        ) {
            Text("Edit")
        }
    }
}

@Composable
private fun Section(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        content()
    }
}

@Composable
fun SuccessDialog(
    visible: Boolean,
    message: String = stringResource(R.string.payment_success),
    onDismiss: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.success),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(stringResource(R.string.back_to_home))
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = AlertDialogDefaults.TonalElevation
        )
    }
}
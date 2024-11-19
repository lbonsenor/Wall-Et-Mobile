package com.example.wall_et_mobile.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.data.mock.MockCards
import com.example.wall_et_mobile.model.CardDetails
import com.example.wall_et_mobile.ui.theme.DarkerGrotesque
import com.example.wall_et_mobile.ui.theme.TransparentGray
import com.example.wall_et_mobile.ui.theme.WallEtTheme
import com.example.wall_et_mobile.ui.theme.White
import kotlinx.coroutines.flow.filter


@Composable
fun CardItem(card: CardDetails, onDelete: () -> Unit) {
    var visible by remember { mutableStateOf(true) }
    val state = rememberSwipeToDismissBoxState(
        positionalThreshold = { distance -> distance * 0.2f }
    )

    AnimatedVisibility(
        visible = visible,
        exit = shrinkHorizontally(animationSpec = tween(300)) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(250.dp)
                        .padding(15.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.error),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                        )
                    }
                }
            }
        )
        {
            CardIndividualItem(card)
        }

    }
    LaunchedEffect(state) {
        snapshotFlow { state.currentValue }
            .filter { it == SwipeToDismissBoxValue.EndToStart }
            .collect { visible = false }
    }
}

@Composable
fun CardIndividualItem(card: CardDetails) {
    var isNumberVisible by remember { mutableStateOf(false) }

    val maskedNumber = if (isNumberVisible) {
        card.cardNumber
    } else {
        card.cardNumber.replace(Regex(".{14}(.{4})"), "**** **** **** $1")
    }

    Card(
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .height(250.dp)
            .fillMaxHeight()
            .padding(15.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = card.backgroundColors,
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                    tileMode = TileMode.Clamp
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 15.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(card.getCardBrand().iconInt),
                    contentDescription = "Card Brand",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(70.dp)
                )
                IconButton(
                    onClick = { isNumberVisible = !isNumberVisible }
                ) {
                    Surface(
                        shape = CircleShape,
                        color = TransparentGray,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isNumberVisible) R.drawable.visibility
                                else R.drawable.visibility_off
                            ),
                            contentDescription = if (isNumberVisible) "Hide card number" else "Show card number",
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            }
                Text(
                    text = maskedNumber,
                    color = White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = DarkerGrotesque,
                    letterSpacing = 2.sp
                )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.cardHolder.uppercase(),
                    color = White,
                    fontSize = 16.sp,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.valid_thru),
                        fontSize = 12.sp,
                        maxLines = 2,
                        modifier = Modifier.width(IntrinsicSize.Min),
                        lineHeight = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.End,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = card.cardExpiration,
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

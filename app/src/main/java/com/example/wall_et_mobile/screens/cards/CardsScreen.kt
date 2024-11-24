package com.example.wall_et_mobile.screens.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wall_et_mobile.MyApplication
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.components.AddCardDialog
import com.example.wall_et_mobile.components.CardList
import com.example.wall_et_mobile.data.mock.MockCards

@Composable
fun CardsScreen(
    innerPadding: PaddingValues,
    viewModel: CardsViewModel = viewModel(factory = CardsViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getCards()
    }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(showDialog) {
        if (!showDialog) {
            viewModel.getCards()
        }
    }

Scaffold(
    modifier = Modifier.padding(innerPadding),
    bottomBar = {
        Box(
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            AddCardButton {
                showDialog = true
            }
        }
    }
) { paddingValues ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        CardList(
            cards = uiState.cards,
            onDeleteCard = { cardToDelete ->
                viewModel.deleteCard(cardToDelete.cardId!!)
            }
        )

        AddCardDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onSubmit = {
                viewModel.addCard(it)
            }
        )
    }
}
}

@Composable
fun AddCardButton(onClick: () -> Unit) {
    androidx.compose.material3.OutlinedButton(
        onClick = { onClick() },
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.add_card)
        )
    }
}

@Preview
@Composable
fun CardsScreenPreview() {
    CardsScreen(innerPadding = PaddingValues(0.dp))
}
package com.example.wall_et_mobile.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wall_et_mobile.R
import com.example.wall_et_mobile.ui.theme.WallEtTheme

@Composable
fun CustomButton(text: String, icon: Int, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
//            .background(color = MaterialTheme.colorScheme.surfaceVariant),
        shape = RectangleShape,
        ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(painter = painterResource(icon), contentDescription = null)
            Text(text = text)
        }

    }
}

@Preview
@Composable
fun PreviewCustomButton() {
    WallEtTheme {
        CustomButton(text = "Transferir", icon = R.drawable.payments, onClick = {})
    }

}
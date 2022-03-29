package com.seadog.discskins.ui.presentation.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seadog.discskins.viewmodels.SkinsViewModel

@Composable
fun FeedbackView(viewModel: SkinsViewModel){
    val context = LocalContext.current

    val padding = 50.dp
    val fontSize = 20.sp

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            Text(
                text = "Enjoying the app ?",
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row {
            Text(
                text = "Anything else you'd like to see?",
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row {
            Text(
                text = "Let us know.",
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(modifier = Modifier.padding(padding)) {
            Card(elevation = 24.dp) {
                Button(
                    content = { Text("Send An Email") },
                    onClick = {
                        viewModel.sendEmail(context)
                    }
                )
            }
        }
    }
}

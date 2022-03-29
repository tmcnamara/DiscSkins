package com.seadog.discskins.ui.presentation

import android.content.ContentResolver
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import io.mockk.mockk
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.seadog.discskins.R
import com.seadog.discskins.ui.presentation.compose.DiscSkinsScreen
import com.seadog.discskins.ui.theme.DiscSkinsTheme
import com.seadog.discskins.viewmodels.SkinsViewModel

@Composable
fun FileLoader(
    viewModel: SkinsViewModel,
    contentResolver: ContentResolver,
    intent: Intent,
    navHostController: NavHostController) {

    var wager by remember { mutableStateOf("0") }
    val isError = wager.toDoubleOrNull() == null
    val padding = 20.dp
    Card{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(padding)
                    .width(200.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dollar_sign),
                        contentDescription = "Dollar amount",
                        modifier = Modifier
                            .height(16.dp)
                            .width(16.dp)
                    )
                },
                value = wager,
                onValueChange = { wager = it },
                label = {
                    Text(
                        "Enter wager per hole",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }
            )
            if (isError) {
                Text(
                    text = "Enter valid number",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(padding)
                )
            }
            Button(
                onClick = {
                    handleSendText(
                        wager,
                        intent.extras,
                        viewModel,
                        contentResolver,
                        navHostController
                    )
                },
                content = { Text("Calculate Round") },
                modifier = Modifier.padding(padding),
                colors = ButtonDefaults.buttonColors(backgroundColor = colors.primary)
            )
        }
    }
}

fun handleSendText(
    wager: String,
    extras: Bundle?,
    viewModel: SkinsViewModel,
    contentResolver: ContentResolver,
    navHostController: NavHostController
) {
    extras?.get(Intent.EXTRA_STREAM).let {
        it as Uri
        contentResolver.openInputStream(it)?.bufferedReader()?.let { reader ->
            viewModel.processCSV(wager.toDouble(), reader)
        }
    }
    navHostController.navigate("${DiscSkinsScreen.RoundListView.name}/insert")
}

@Preview(name="Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name="Dark Mode", showBackground = true)
@Composable
fun FileLoadScreenPreview(
) {
    DiscSkinsTheme() {
        FileLoader(
            viewModel = mockk(),
            contentResolver = mockk(),
            intent = mockk(),
            navHostController = mockk()
        )
    }
}

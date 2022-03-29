package com.seadog.discskins.ui.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.seadog.discskins.util.FileShareUtil


@Composable
fun AppMenu(navHostController: NavHostController) {
    val context = LocalContext.current

    val fileShareUtil = FileShareUtil()
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.Menu, contentDescription = "Localized description")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(onClick = {
                navHostController.navigate(DiscSkinsScreen.RoundListView.name)
                expanded = false
            }) {
                Text("Rounds")
            }
            DropdownMenuItem(onClick = {
                navHostController.navigate(DiscSkinsScreen.FeedbackView.name)
                expanded = false
            }) {
                Text("Feedback")
            }
            DropdownMenuItem(onClick = {
                fileShareUtil.showHelpVideo(context)
                expanded = false
            }) {
                Text("How To Use")
            }
        }
    }
}

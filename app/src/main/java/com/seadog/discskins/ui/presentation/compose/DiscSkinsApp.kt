package com.seadog.discskins.ui.presentation

import android.content.ContentResolver
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seadog.discskins.ui.presentation.compose.AppMenu
import com.seadog.discskins.ui.presentation.compose.DiscSkinsScreen
import com.seadog.discskins.ui.presentation.compose.FeedbackView
import com.seadog.discskins.ui.theme.DiscSkinsTheme
import com.seadog.discskins.util.FileShareUtil
import com.seadog.discskins.viewmodels.SkinsViewModel

@Composable
fun DiscSkinsApp(
    intent: Intent,
    viewModel: SkinsViewModel,
    contentResolver: ContentResolver
) {
    val view = LocalView.current
    val context = LocalContext.current
    val navController = rememberNavController()

    DiscSkinsTheme {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Disc Skins", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        AppMenu(navController)
                    },
                    actions = {
                            IconButton(onClick = {
                                viewModel.shareFile(view, context)
                            }) {
                                Icon(Icons.Default.Share, contentDescription = "Share View")
                            }
                        }
                )
            }
        ) { innerPadding ->
            DiscSkinsNavHost(
                navController,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                viewModel,
                contentResolver,
                intent
            )
        }
    }



}

@Composable
fun DiscSkinsNavHost(navController: NavHostController,
                     modifier: Modifier = Modifier,
                     viewModel: SkinsViewModel,
                     contentResolver: ContentResolver,
                     intent: Intent
) {

    val startDestination =
        if (intent.action == Intent.ACTION_SEND)
            Intent.ACTION_SEND
        else
            DiscSkinsScreen.RoundListView.name

    if (navController.currentDestination != null){
        navController.graph.clear()
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(Intent.ACTION_SEND) {
            FileLoader(viewModel, contentResolver, intent, navController)
        }
        composable(DiscSkinsScreen.RoundListView.name){
            RoundListScreen(viewModel, navController)
        }
        composable("${DiscSkinsScreen.RoundListView.name}/insert"){
            RoundListScreen(viewModel, navController, doAnimation = true)
        }
        composable("${DiscSkinsScreen.RoundView.name}/{roundId}"){
            RoundScreen(viewModel, it.arguments?.getString("roundId"))
        }
        composable(DiscSkinsScreen.FeedbackView.name){
            FeedbackView(viewModel)
        }
    }
}
package com.seadog.discskins.ui.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.seadog.discskins.viewmodels.SkinsViewModel

@Composable
fun RoundListScreen(
    viewModel: SkinsViewModel,
    navHostController: NavHostController,
    doAnimation: Boolean = false) {

    val rounds = viewModel.getRounds().observeAsState(arrayListOf())

    val newRoundId = viewModel.getLatestRoundId().observeAsState(-1L)

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray ),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
    ){
        items(rounds.value) { round ->
            val performAnimation = doAnimation && round.round.id == newRoundId.value
            RoundSummaryCard(round, viewModel, navHostController, doAnimation = performAnimation)
        }
    }

}

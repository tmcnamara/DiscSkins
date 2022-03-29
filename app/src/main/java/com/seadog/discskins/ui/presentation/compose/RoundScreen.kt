package com.seadog.discskins.ui.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.seadog.discskins.ui.presentation.compose.LandscapeRoundScreen
import com.seadog.discskins.ui.presentation.compose.PortraitRoundScreen
import com.seadog.discskins.viewmodels.SkinsViewModel

@Composable
fun RoundScreen(viewModel: SkinsViewModel, roundId: String?) {

    roundId?.let {
        val round = viewModel.getRound(roundId.toLong()).observeAsState()

        BoxWithConstraints() {
            round.value?.let {
                if (maxWidth > 400.dp) {
                    LandscapeRoundScreen(it)
                } else {
                    PortraitRoundScreen(it)
                }
            }
        }
    }
}


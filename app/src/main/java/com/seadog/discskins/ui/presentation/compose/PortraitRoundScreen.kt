package com.seadog.discskins.ui.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seadog.discskins.domain.model.RoundWithScorecards

@Composable
fun PortraitRoundScreen(round: RoundWithScorecards){
    Column(modifier = Modifier.verticalScroll (rememberScrollState())){
        RoundHeader(round)
        RoundHoleView(startHole = 1, endHole = 9, round = round)
        var endHole = 9
        while (round.round.numOfHoles > endHole) {
            RoundHoleView(startHole = endHole+1, endHole = endHole+9, round = round)
            endHole += 9
        }
    }
}
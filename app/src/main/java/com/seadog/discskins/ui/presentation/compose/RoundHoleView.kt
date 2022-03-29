package com.seadog.discskins.ui.presentation.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.preview.RoundProvider
import com.seadog.discskins.util.SkinsCalculator

@Composable
fun RoundHoleView(startHole: Int, endHole: Int, round: RoundWithScorecards) {
    val topFontSize = 12.sp
    val bottomFontSize = 15.sp
    val rowHeight = 30.dp

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val boxWidth = ((screenWidth*5)/6)/(endHole-startHole+1)-1.dp

    val colorMap = SkinsCalculator().calculateScorecardColors(round, isSystemInDarkTheme())

    Column(
        modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp).horizontalScroll(rememberScrollState())) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight)) {
            Text("Hole",
                fontSize = topFontSize,
                modifier = Modifier.width(screenWidth/6),
                textAlign = TextAlign.End
            )
            for (holeNum in startHole..endHole) {
                Text("$holeNum",
                    fontSize = topFontSize,
                    modifier = Modifier.width(boxWidth),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight)) {
            Text("Par",
                fontSize = topFontSize,
                modifier = Modifier.width(screenWidth/6),
                textAlign = TextAlign.End
            )
            for (holeNum in startHole..endHole) {
                Text("${round.round.parPerHole[holeNum-1]}",
                    fontSize = topFontSize,
                    modifier = Modifier.width(boxWidth),
                    textAlign = TextAlign.Center
                )
            }
        }
        Divider()
        round.scorecards.forEach { scorecard ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)) {
                Text(scorecard.playerName,
                    fontSize = bottomFontSize,
                    modifier = Modifier.width(screenWidth/6),
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                for (holeNum in startHole..endHole) {
                    Box(Modifier.background(
                        colorMap[scorecard.id]?.get(holeNum-1) ?: MaterialTheme.colors.surface
                    )) {
                        Text(
                            "${scorecard.scoresPerHole[holeNum-1]}",
                            fontSize = bottomFontSize,
                            modifier = Modifier.width(boxWidth),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun RoundHoleViewPreview(
    @PreviewParameter(RoundProvider::class) roundWithScorecards: RoundWithScorecards
) {
    RoundHoleView(1,18, roundWithScorecards)
}
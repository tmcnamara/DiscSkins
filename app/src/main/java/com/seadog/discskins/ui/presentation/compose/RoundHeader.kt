package com.seadog.discskins.ui.presentation.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seadog.discskins.R
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.preview.RoundProvider
import com.seadog.discskins.ui.presentation.getEarningsText
import com.seadog.discskins.ui.presentation.getEarningsTextColor
import com.seadog.discskins.ui.presentation.numFormat
import com.seadog.discskins.util.DateUtil

@Composable
fun RoundHeader(roundWithScorecards: RoundWithScorecards) {
    val dateUtil = DateUtil()
    val headerFontSize = 16.sp
    val fontSize = 14.sp

    Column( modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()){
        Row(modifier = Modifier.padding(horizontal = 5.dp)) {
            Text(
                roundWithScorecards.round.location,
                fontSize = headerFontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                " - ${roundWithScorecards.round.layout}",
                fontSize = headerFontSize
            )
        }
        Row(modifier = Modifier.padding(horizontal = 5.dp)) {
            Text(
                dateUtil.reformat(roundWithScorecards.round.date),
                fontSize = fontSize
            )
            Text(
                " - ${roundWithScorecards.round.numOfHoles} holes",
                fontSize = fontSize
            )
            Text(
                " - ${numFormat.format(roundWithScorecards.round.wager)} per hole",
                fontSize = fontSize
            )
            Text(
                " - ${roundWithScorecards.round.holesPushed} push",
                fontSize = fontSize
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))

        roundWithScorecards.scorecards.sortedByDescending { it.earningsDifferential }
            .forEach { scorecard ->
            Row(modifier = Modifier.padding(horizontal = 10.dp))
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_discgolfbasket),
                    contentDescription = "player",
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
                Text(scorecard.playerName,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${getEarningsText(scorecard)} - ${scorecard.skinsTotal} skins",
                    fontSize = fontSize,
                    color = getEarningsTextColor(scorecard.earningsDifferential, MaterialTheme.colors),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }

    }
}


@Preview
@Composable
fun RoundHeaderPreview(
    @PreviewParameter(RoundProvider::class) roundWithScorecards: RoundWithScorecards
) {
    RoundHeader(roundWithScorecards)
}
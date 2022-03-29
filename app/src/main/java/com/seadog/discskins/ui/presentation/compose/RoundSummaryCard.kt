package com.seadog.discskins.ui.presentation

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.preview.RoundProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.seadog.discskins.domain.model.Scorecard
import com.seadog.discskins.ui.presentation.compose.DeleteDialog
import com.seadog.discskins.ui.presentation.compose.DiscSkinsScreen
import com.seadog.discskins.ui.theme.DiscSkinsTheme
import com.seadog.discskins.util.DateUtil
import com.seadog.discskins.viewmodels.SkinsViewModel
import java.text.DecimalFormat

val numFormat = DecimalFormat("$##0.00")

@Composable
fun RoundSummaryCard(roundWithScorecards: RoundWithScorecards,
                     viewModel: SkinsViewModel? = null,
                     navHostController: NavHostController? = null,
                     doAnimation: Boolean = false) {

    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }

    Surface(
        elevation = 24.dp,
        modifier = Modifier
            .padding(5.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { navHostController?.navigate("${DiscSkinsScreen.RoundView.name}/${roundWithScorecards.round.id}") },
                    onLongPress = { setShowDialog(true) }
                )
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        DeleteDialog(roundWithScorecards.round,
            viewModel = viewModel,
            showDialog = showDialog,
            setShowDialog = setShowDialog)

        if (doAnimation) {
            Pulsating {
                CoreCard(roundWithScorecards)
            }
        } else {
            CoreCard(roundWithScorecards)
        }
    }
}

@Composable
fun CoreCard(roundWithScorecards: RoundWithScorecards){
    val headerFontSize = 14.sp
    val fontSize = 12.sp
    val dateUtil = DateUtil()

    Card(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
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
            Row {
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
            Row(modifier = Modifier.fillMaxWidth()) {
                roundWithScorecards.scorecards.sortedByDescending { it.earningsDifferential }
                    .forEach {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                it.playerName,
                                fontWeight = FontWeight.Bold,
                                fontSize = fontSize,
                                modifier = Modifier.padding(horizontal = 15.dp)
                            )
                            Text(
                                "${getEarningsText(it)}",
                                fontSize = fontSize,
                                color = getEarningsTextColor(
                                    it.earningsDifferential,
                                    MaterialTheme.colors
                                ),
                                modifier = Modifier.padding(horizontal = 15.dp)
                            )
                            Text(
                                "${it.skinsTotal} skins",
                                fontSize = fontSize,
                                color = getEarningsTextColor(
                                    it.earningsDifferential,
                                    MaterialTheme.colors
                                ),
                                modifier = Modifier.padding(horizontal = 15.dp)
                            )
                        }
                    }
            }
        }
    }
}


@Composable
fun Pulsating(pulseFraction: Float = 0.8f, content: @Composable () -> Unit) {

    val animateScale = remember { Animatable(pulseFraction) }
    val requester = FocusRequester()

    LaunchedEffect(animateScale) {
        animateScale.animateTo(
            targetValue = 1f,
            animationSpec = repeatable(
                iterations = 4,
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(modifier = Modifier.scale(animateScale.value).focusRequester(requester)) {
        content()
    }
}

fun getEarningsTextColor(earnings: Double, colors: Colors): Color {
    return when {
            earnings == 0.0 -> colors.onSurface
            earnings > 0 -> colors.primary
            else -> colors.secondary
        }
}

fun getEarningsText(scorecard: Scorecard): String {
    return if (scorecard.earningsDifferential < 0) {
        "-${numFormat.format(scorecard.earningsDifferential*-1)}"
    } else {
        numFormat.format(scorecard.earningsDifferential)
    }
}


@Preview(name="Light Mode", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name="Dark Mode", showBackground = true)
@Composable
fun RoundSummaryCardPreview(
    @PreviewParameter(RoundProvider::class) roundWithScorecards: RoundWithScorecards
) {
    DiscSkinsTheme() {
        RoundSummaryCard(roundWithScorecards)
    }
}
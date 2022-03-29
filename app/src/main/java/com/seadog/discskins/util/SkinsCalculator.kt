package com.seadog.discskins.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.ui.theme.*
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.min
import kotlin.math.round

class SkinsCalculator {

    fun calculate(roundInfo: RoundWithScorecards): RoundWithScorecards {
        val skinsMap = hashMapOf<String,MutableList<Int>>()
        val scoresMap = hashMapOf<String, List<Int>>()

        for (scorecard in roundInfo.scorecards) {
            if (skinsMap.contains(scorecard.playerName)) {
                scorecard.playerName = "${scorecard.playerName}-2"
            }
            skinsMap[scorecard.playerName] = mutableListOf()
            scoresMap[scorecard.playerName] = scorecard.scoresPerHole
        }

        var currentSkins = 0

        for (holeNumber in 0 until roundInfo.round.numOfHoles) {
            currentSkins++

            println("hole number = ${holeNumber+1}, currentSkins = $currentSkins")
            var min = MAX_VALUE
            scoresMap.keys.forEach {
                scoresMap[it]?.get(holeNumber)?.let { score ->
                    min = min(score, min)
                }
            }
            var playerCount = 0
            scoresMap.keys.forEach {
                scoresMap[it]?.get(holeNumber)?.let { score ->
                    if (score == min) playerCount++
                }
            }

            scoresMap.keys.forEach { scoreId ->
                scoresMap[scoreId]?.get(holeNumber)?.let { score ->
                    if (playerCount == 1 && score == min) {
                        skinsMap[scoreId]?.add(currentSkins)
                        println("scorecard $scoreId won $currentSkins skins on hole ${holeNumber + 1}")
                        currentSkins = 0
                    } else {
                        skinsMap[scoreId]?.add(0)
                    }
                }
            }
        }

        roundInfo.round.holesPushed = currentSkins
        val totalWager = roundInfo.round.wager * (roundInfo.round.numOfHoles - roundInfo.round.holesPushed)

        roundInfo.scorecards.forEach { scorecard ->
            skinsMap[scorecard.playerName]?.let {
                scorecard.skinsPerHole = it.toList()
                scorecard.skinsTotal = it.sum()
                scorecard.earningsTotal = it.sum() * roundInfo.round.wager * roundInfo.scorecards.size
                scorecard.earningsDifferential = scorecard.earningsTotal - totalWager
            }
        }

        return roundInfo
    }

    fun calculateScorecardColors(roundInfo: RoundWithScorecards, darkTheme: Boolean): Map<Long, List<Color>> {
        val darkGreen = if (darkTheme) Green600 else Green700
        val lightGreen = if (darkTheme) Green300 else Green200

        val colorMap = mutableMapOf<Long, MutableList<Color>>()
        roundInfo.scorecards.forEach {
            colorMap[it.id] = MutableList(roundInfo.round.numOfHoles) {
                if (darkTheme) Black else White }
        }

        var lastIndex = 0

        for (holeNum in 0 until roundInfo.round.numOfHoles) {
            roundInfo.scorecards.forEach { scorecard ->
                if (scorecard.skinsPerHole[holeNum] > 0) {
                    colorMap[scorecard.id]?.let { colorList ->
                        colorList[holeNum] = darkGreen
                        for (index in lastIndex until holeNum) {
                            colorList[index] = lightGreen
                        }
                    }
                    lastIndex = holeNum + 1
                }
            }
        }
        return colorMap
    }
}
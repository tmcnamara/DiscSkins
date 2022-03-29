package com.seadog.discskins.util

import androidx.compose.ui.graphics.Color
import com.seadog.discskins.domain.model.Round
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.domain.model.Scorecard
import org.junit.Assert.assertEquals
import org.junit.Test

class SkinsCalculatorTest {

    val testRound =
        RoundWithScorecards(
            round = Round(
                id = 1,
                location = "Hanns Park",
                layout = "Odds and Evens",
                date = "2022-02-14",
                numOfHoles = 18,
                par = 54,
                wager = 3.0,
                parPerHole = listOf(3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3)
            ),
            scorecards = listOf(
                Scorecard(
                    id = 1,
                    roundId = 1,
                    playerName = "Hamrod55",
                    score = 50,
                    skinsTotal = 0,
                    scoresPerHole = listOf(2,3,2,3,3,4,2,2,3,3,2,3,2,3,3,2,4,4),
                    skinsPerHole = listOf(1,0,0,0,0,0,1,1,0,0,0,0,0,6,0,2,0,0)
                ),
                Scorecard(
                    id = 2,
                    roundId = 1,
                    playerName = "David",
                    score = 56,
                    skinsTotal = 0,
                    scoresPerHole = listOf(3,3,2,4,3,3,4,3,3,3,2,3,2,5,3,3,3,4),
                    skinsPerHole = listOf(0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,1,0)
                ),
                Scorecard(
                    id = 3,
                    roundId = 1,
                    playerName = "Tim",
                    score = 62,
                    skinsTotal = 0,
                    scoresPerHole = listOf(3,3,3,3,3,4,3,3,3,3,3,5,3,4,3,5,4,4),
                    skinsPerHole = listOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
                )
            )
        )


    @Test
    fun test_calculate() {
        val result = SkinsCalculator().calculate(testRound)

        val scorecard1 = result.scorecards[0]
        val scorecard2 = result.scorecards[1]
        val scorecard3 = result.scorecards[2]

        assertEquals(11, scorecard1.skinsTotal)
        assertEquals(99.0, scorecard1.earningsTotal, 0.01)
        assertEquals(6, scorecard2.skinsTotal)
        assertEquals(54.0, scorecard2.earningsTotal, 0.01)
        assertEquals(0, scorecard3.skinsTotal)
        assertEquals(0.0, scorecard3.earningsTotal, 0.01)
        assertEquals(1, result.round.holesPushed)
        assertEquals(1,scorecard1.skinsPerHole[0])
        assertEquals(5,scorecard2.skinsPerHole[5])
        assertEquals(1,scorecard1.skinsPerHole[6])
    }

    @Test
    fun test_calculateScorecardColors(){
        val darkGreen = Color(0XFF76d275)
        val lightGreen = Color(0XFFe1ffb1)

        val result = SkinsCalculator().calculateScorecardColors(testRound, false)

        assertEquals(darkGreen,result[1]?.get(0))
        assertEquals(Color.White,result[2]?.get(0))
        assertEquals(Color.White,result[3]?.get(0))
        assertEquals(lightGreen,result[2]?.get(1))
        assertEquals(lightGreen,result[2]?.get(2))
        assertEquals(darkGreen,result[2]?.get(5))
    }
}
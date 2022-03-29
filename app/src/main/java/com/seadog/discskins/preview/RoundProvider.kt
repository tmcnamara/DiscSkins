package com.seadog.discskins.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.seadog.discskins.domain.model.Round
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.domain.model.Scorecard

class RoundProvider: PreviewParameterProvider<RoundWithScorecards> {
    override val values = sequenceOf(
        RoundWithScorecards(
                    round = Round(
                        id=2,
                        location = "Hanns Park",
                        layout = "Odds and Evens",
                        date = "2022-01-29 08:32",
                        numOfHoles=18,
                        par=54,
                        wager=3.0,
                        parPerHole= listOf(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3),
                        holesPushed=1),
            scorecards = listOf(
                Scorecard(
                    id=4,
                    roundId=2,
                    playerName="David",
                    score=56,
                    skinsTotal=6,
                    earningsTotal=54.0,
                    earningsDifferential = 3.0,
                    scoresPerHole= listOf(3, 3, 2, 4, 3, 3, 4, 3, 3, 3, 2, 3, 2, 5, 3, 3, 3, 4),
                    skinsPerHole=listOf(0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0)
                ),
                Scorecard(
                    id=5,
                    roundId=2,
                    playerName = "Hamrod55",
                    score=50,
                    skinsTotal=11,
                    earningsTotal=99.0,
                    earningsDifferential = 48.0,
                    scoresPerHole=listOf(2, 3, 2, 3, 3, 4, 2, 2, 3, 3, 2, 3, 2, 3, 3, 2, 4, 4),
                    skinsPerHole=listOf(1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 6, 0, 2, 0, 0)
                ),
                Scorecard(
                    id=6,
                    roundId=2,
                    playerName="Tim",
                    score=62,
                    skinsTotal=0,
                    earningsTotal=0.0,
                    earningsDifferential = -51.0,
                    scoresPerHole=listOf(3, 3, 3, 3, 3, 4, 3, 3, 3, 3, 3, 5, 3, 4, 3, 5, 4, 4),
                    skinsPerHole=listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                )
            )
        )
    )
}
package com.seadog.discskins.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class RoundWithScorecards(
    @Embedded
    val round: Round,
    @Relation(
        parentColumn = "id",
        entityColumn = "round_id"
    )
    val scorecards: List<Scorecard>
)
package com.seadog.discskins.domain.model

import androidx.room.*

@Entity(
    tableName = "scorecard",
    foreignKeys = [
        ForeignKey(
            entity = Round::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("round_id"),
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class Scorecard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "round_id")
    var roundId: Long,

    @ColumnInfo(name = "player")
    var playerName: String,

    @ColumnInfo(name = "score")
    val score: Int,

    @ColumnInfo(name = "skins_total")
    var skinsTotal: Int = 0,

    @ColumnInfo(name = "earnings_total")
    var earningsTotal: Double = 0.0,

    @ColumnInfo(name = "earnings_differental")
    var earningsDifferential: Double = 0.0,

    @ColumnInfo(name = "scores_per_hole")
    var scoresPerHole: List<Int> = listOf(),

    @ColumnInfo(name = "skins_per_hole")
    var skinsPerHole: List<Int> = listOf()
)
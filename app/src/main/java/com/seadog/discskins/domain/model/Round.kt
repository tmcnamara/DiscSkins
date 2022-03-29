package com.seadog.discskins.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "round",
    indices = [
        Index(value = ["location", "layout", "date"], unique = true)
    ]
)
data class Round(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "layout")
    val layout: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "num_of_holes")
    var numOfHoles: Int = 0,

    @ColumnInfo(name = "par")
    val par: Int,

    @ColumnInfo(name = "wager")
    var wager: Double = 0.0,

    @ColumnInfo(name = "par_per_hole")
    var parPerHole: List<Int> = listOf(),

    @ColumnInfo(name = "holes_pushed")
    var holesPushed: Int = 0
)
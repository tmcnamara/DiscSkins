package com.seadog.discskins.util

import com.seadog.discskins.repository.SkinsRepository
import com.seadog.discskins.domain.model.Round
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.domain.model.Scorecard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader

class CSVProcessor(private val repository: SkinsRepository) {

    suspend fun processCSV(wager: Double, reader: BufferedReader) {
        val csvParser = CSVParser(reader, CSVFormat.DEFAULT)
        val skinsCalculator = SkinsCalculator()

        val headers: ArrayList<String> = arrayListOf()

        var round: Round? = null
        val scorecards: MutableList<Scorecard> = mutableListOf()

        var roundId = -1L

        withContext(Dispatchers.IO) {
            csvParser.records.forEach { record ->
                if (record.recordNumber == 1L) {
                    headers.addAll(record.toList())
                } else {
                    if (record[headers.indexOf("PlayerName")] == "Par") {
                        round = Round(
                            location = record[headers.indexOf("CourseName")],
                            layout = record[headers.indexOf("LayoutName")],
                            date = record[headers.indexOf("Date")],
                            par = record[headers.indexOf("Total")].toInt(),
                            wager = wager
                        )
                        val parPerHole = mutableListOf<Int>()
                        for (i in headers.indexOf("Hole1") until headers.size) {
                            parPerHole.add(record[i].toInt())
                        }
                        round?.numOfHoles = parPerHole.size
                        round?.parPerHole = parPerHole

                    } else {
                        val scorecard = Scorecard(
                            roundId = 0,
                            playerName = record[headers.indexOf("PlayerName")],
                            score = record[headers.indexOf("Total")].toInt()
                        )
                        val scoresPerHole = mutableListOf<Int>()
                        for (i in headers.indexOf("Hole1") until headers.size) {
                            scoresPerHole.add(record[i].toInt())
                        }
                        scorecard.scoresPerHole = scoresPerHole
                        scorecards.add(scorecard)
                    }
                }
            }.also {
                round?.let {
                    val roundInfo = RoundWithScorecards(
                        round = it,
                        scorecards = scorecards
                    )
                    val result = skinsCalculator.calculate(roundInfo)
                    repository.delete(it)
                    roundId = repository.insert(it)

                    result.scorecards.forEach { scorecard ->
                        scorecard.roundId = roundId
                        repository.insert(scorecard)
                    }
                }
            }
        }
    }
}
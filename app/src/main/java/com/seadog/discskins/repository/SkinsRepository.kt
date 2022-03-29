package com.seadog.discskins.repository

import androidx.lifecycle.LiveData
import com.seadog.discskins.repository.dao.SkinsDao
import com.seadog.discskins.domain.model.Round
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.domain.model.Scorecard

class SkinsRepository(private val roundDao: SkinsDao) {

    suspend fun delete(round: Round) {
        roundDao.delete(round.date, round.location, round.layout)
    }

    suspend fun insert(round: Round): Long {
        return roundDao.insert(round)
    }

    suspend fun insert(scorecard: Scorecard): Long {
        return roundDao.insert(scorecard)
    }

    fun getRoundInfo(id: Long): LiveData<RoundWithScorecards> {
        return roundDao.getRoundWithScorecards(id)
    }

    fun getRounds(): LiveData<List<RoundWithScorecards>> {
        return roundDao.getRoundsWithScorecards()
    }

    fun getLatestRoundId(): LiveData<Long> {
        return roundDao.getMaxRoundId()
    }
}
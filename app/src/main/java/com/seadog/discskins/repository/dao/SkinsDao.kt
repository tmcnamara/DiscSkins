package com.seadog.discskins.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.seadog.discskins.domain.model.Round
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.domain.model.Scorecard
import kotlinx.coroutines.flow.Flow

@Dao
interface SkinsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(round: Round): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scorecard: Scorecard): Long

    @Transaction
    @Query( "DELETE from round WHERE date = :date and location = :location and layout = :layout")
    suspend fun delete(date: String, location: String, layout: String)

    @Delete
    suspend fun delete(round: Round)

    @Query("SELECT * FROM round WHERE id = :id and id IN (SELECT round_id FROM scorecard)")
    fun getRoundWithScorecards(id: Long): LiveData<RoundWithScorecards>

    @Query("SELECT * FROM round WHERE id IN (SELECT round_id FROM scorecard) ORDER BY date DESC")
    fun getRoundsWithScorecards(): LiveData<List<RoundWithScorecards>>

    @Query( "SELECT MAX(id) FROM round")
    fun getMaxRoundId(): LiveData<Long>
}
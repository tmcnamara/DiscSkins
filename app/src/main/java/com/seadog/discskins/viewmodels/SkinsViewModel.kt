package com.seadog.discskins.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.seadog.discskins.domain.model.Round
import com.seadog.discskins.domain.model.RoundWithScorecards
import com.seadog.discskins.repository.SkinsRepository
import com.seadog.discskins.util.CSVProcessor
import com.seadog.discskins.util.EmailUtil
import com.seadog.discskins.util.FileShareUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.BufferedReader
import javax.inject.Inject

@HiltViewModel
class SkinsViewModel
    @Inject constructor (
        private val csvProcessor: CSVProcessor,
        private val repository: SkinsRepository
    ): ViewModel() {

    private val fileShareUtil = FileShareUtil()
    private val emailUtil = EmailUtil()

    fun processCSV(wager: Double, reader: BufferedReader) {
        viewModelScope.launch {
            csvProcessor.processCSV(wager, reader)
        }
    }

    fun getRounds(): LiveData<List<RoundWithScorecards>> {
        return repository.getRounds()
    }

    fun getRound(id: Long): LiveData<RoundWithScorecards> {
        return repository.getRoundInfo(id)
    }

    fun getLatestRoundId(): LiveData<Long> {
        return repository.getLatestRoundId()
    }

    fun delete(round: Round) {
        viewModelScope.launch {
            repository.delete(round)
        }
    }

    fun shareFile(view: View, context: Context) {
        viewModelScope.launch {
            fileShareUtil.shareFile(view.findViewWithTag("shareTag"), context)
        }
    }

    fun sendEmail(context: Context) {
        viewModelScope.launch {
            emailUtil.sendEmail(context)
        }
    }



}
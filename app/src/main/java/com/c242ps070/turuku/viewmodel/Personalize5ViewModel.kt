package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import com.c242ps070.turuku.data.local.room.entity.SleepHistoryEntity
import com.c242ps070.turuku.data.remote.request.HistoryRequest
import com.c242ps070.turuku.data.remote.response.SuccessResponse
import com.c242ps070.turuku.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class Personalize5ViewModel(
    private val historyRepository: HistoryRepository,
    private val userPreference: UserPreference,
): ViewModel() {
    fun getUserLoggedIn(): LiveData<UserPreferenceModel> = userPreference.getUser().asLiveData()

    fun savePhysicalActivityAndDailySteps(
        physicalActivity: Int,
        dailySteps: Int
    ) {
        viewModelScope.launch {
            userPreference.savePhysicalActivityAndDailySteps(
                physicalActivity,
                dailySteps
            )
        }
    }

    fun insertSleepHistory(sleepHistory: SleepHistoryEntity) {
        viewModelScope.launch {
            historyRepository.insertSleepHistoryRoom(sleepHistory)
        }
    }

    fun addHistory(history: HistoryRequest): LiveData<Result<SuccessResponse>> =
        historyRepository.addHistory(history)
}
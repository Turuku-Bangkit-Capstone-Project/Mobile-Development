package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import com.c242ps070.turuku.data.remote.request.SleepRecommendationRequest
import com.c242ps070.turuku.data.remote.response.SleepRecommendationResponse
import com.c242ps070.turuku.data.repository.MachineLearningRepository

class Personalize5ViewModel(
    private val machineLearningRepository: MachineLearningRepository,
    private val userPreference: UserPreference
): ViewModel() {
    fun getSleepRecommendation(
        request: SleepRecommendationRequest
    ): LiveData<Result<SleepRecommendationResponse>> =
        machineLearningRepository.getSleepRecommendation(request)

    fun getUserLoggedIn(): LiveData<UserPreferenceModel> = userPreference.getUser().asLiveData()
}
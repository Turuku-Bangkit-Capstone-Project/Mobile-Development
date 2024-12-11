package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import com.c242ps070.turuku.data.remote.response.SleepRecommendationResponse
import com.c242ps070.turuku.data.remote.response.UserDataResponse
import com.c242ps070.turuku.data.repository.MachineLearningRepository
import com.c242ps070.turuku.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val machineLearningRepository: MachineLearningRepository,
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
): ViewModel() {
    fun getUserLoggedIn(): LiveData<UserPreferenceModel> = userPreference.getUser().asLiveData()

    fun getSleepRecommendation(): LiveData<Result<SleepRecommendationResponse>> =
        machineLearningRepository.getSleepRecommendation()

    fun getUserData(): LiveData<Result<List<UserDataResponse>>> = userRepository.getUserData()

    fun saveUserData(
        age: Int,
        gender: String,
        chronotype: String
    ) {
        viewModelScope.launch {
            userPreference.saveAgeAndGender(age, gender)
            userPreference.saveChronotype(chronotype)
        }
    }
}
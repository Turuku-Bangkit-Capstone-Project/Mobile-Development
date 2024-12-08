package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import com.c242ps070.turuku.data.remote.request.ChronotypeRequest
import com.c242ps070.turuku.data.remote.response.ChronotypeResponse
import com.c242ps070.turuku.data.remote.response.SuccessResponse
import com.c242ps070.turuku.data.remote.response.UpsertUserDataRequest
import com.c242ps070.turuku.data.repository.MachineLearningRepository
import com.c242ps070.turuku.data.repository.UserRepository
import kotlinx.coroutines.launch

class Personalize5ViewModel(
    private val machineLearningRepository: MachineLearningRepository,
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
): ViewModel() {
    fun getChronotype(request: ChronotypeRequest): LiveData<Result<ChronotypeResponse>> =
        machineLearningRepository.getChronotype(request)

    fun getUserLoggedIn(): LiveData<UserPreferenceModel> = userPreference.getUser().asLiveData()

    fun saveChronotype(chronotype: String) {
        viewModelScope.launch {
            userPreference.saveChronotype(chronotype)
        }
    }

    fun insertUserData(
        request: UpsertUserDataRequest
    ): LiveData<Result<SuccessResponse>> = userRepository.upsertUserData(request)
}
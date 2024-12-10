package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import com.c242ps070.turuku.data.remote.request.LoginRequest
import com.c242ps070.turuku.data.remote.response.LoginResponse
import com.c242ps070.turuku.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userPreference: UserPreference
): ViewModel() {
    fun login(loginRequest: LoginRequest): LiveData<Result<LoginResponse>> =
        authRepository.login(loginRequest)

    fun saveUserLoggedIn(userPreferenceModel: UserPreferenceModel) {
        viewModelScope.launch {
            userPreference.saveUser(userPreferenceModel)
        }
    }

    fun getUserLoggedIn(): LiveData<UserPreferenceModel> = userPreference.getUser().asLiveData()
}
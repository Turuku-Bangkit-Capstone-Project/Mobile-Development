package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.remote.request.LoginRequest
import com.c242ps070.turuku.data.remote.response.LoginResponse
import com.c242ps070.turuku.data.repository.AuthRepository

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    fun login(loginRequest: LoginRequest): LiveData<Result<LoginResponse>> =
        authRepository.login(loginRequest)
}
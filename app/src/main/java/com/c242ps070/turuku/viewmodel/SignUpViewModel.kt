package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.remote.request.RegisterRequest
import com.c242ps070.turuku.data.remote.response.SuccessResponse
import com.c242ps070.turuku.data.repository.AuthRepository

class SignUpViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    fun signUp(registerRequest: RegisterRequest): LiveData<Result<SuccessResponse>> =
        authRepository.register(registerRequest)
}
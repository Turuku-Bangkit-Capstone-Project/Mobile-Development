package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.remote.request.ChangePasswordRequest
import com.c242ps070.turuku.data.remote.response.SuccessResponse
import com.c242ps070.turuku.data.repository.UserRepository

class ChangepassViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun changePassword(request: ChangePasswordRequest) = liveData {
        emit(Result.Loading)
        try {
            val response = userRepository.changePassword(request)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}
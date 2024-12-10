package com.c242ps070.turuku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c242ps070.turuku.data.remote.request.RegisterRequest
import com.c242ps070.turuku.data.remote.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.remote.request.LoginRequest
import com.c242ps070.turuku.data.remote.response.ErrorResponse
import com.c242ps070.turuku.data.remote.response.LoginResponse
import com.c242ps070.turuku.data.remote.response.RefreshTokenResponse
import com.c242ps070.turuku.data.remote.response.SuccessResponse

class AuthRepository(
    private val apiService: ApiService
) {
    fun register(registerRequest: RegisterRequest): LiveData<Result<SuccessResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(registerRequest)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message ?: ""
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.d("AuthRepository", "register: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(loginRequest: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(loginRequest)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message ?: ""
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.d("AuthRepository", "login: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun logout() = apiService.logout()

    fun refreshToken(): LiveData<Result<RefreshTokenResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.refreshToken()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message ?: ""
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.d("AuthRepository", "refreshToken: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
}
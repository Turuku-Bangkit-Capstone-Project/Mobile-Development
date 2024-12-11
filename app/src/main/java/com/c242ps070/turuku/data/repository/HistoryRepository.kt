package com.c242ps070.turuku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.room.TurukuDatabase
import com.c242ps070.turuku.data.remote.request.HistoryRequest
import com.c242ps070.turuku.data.remote.response.ErrorResponse
import com.c242ps070.turuku.data.remote.response.HistoryResponse
import com.c242ps070.turuku.data.remote.response.SuccessResponse
import com.c242ps070.turuku.data.remote.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException

class HistoryRepository(
    private val apiService: ApiService,
    private val database: TurukuDatabase
) {
    fun getHistory(): LiveData<Result<List<HistoryResponse>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getHistory()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message ?: ""
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.d("UserRepository", "getHistory: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addHistory(request: HistoryRequest): LiveData<Result<SuccessResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addHistory(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message ?: ""
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.d("UserRepository", "getHistory: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
}
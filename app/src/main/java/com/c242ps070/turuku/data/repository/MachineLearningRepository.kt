package com.c242ps070.turuku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.remote.response.ChronotypeResponse
import com.c242ps070.turuku.data.remote.response.ErrorResponse
import com.c242ps070.turuku.data.remote.response.SleepRecommendationResponse
import com.c242ps070.turuku.data.remote.retrofit.ApiService
import com.google.gson.Gson
import retrofit2.HttpException

class MachineLearningRepository(
    private val apiService: ApiService
) {
    fun getSleepRecommendation(): LiveData<Result<SleepRecommendationResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.sleepRecommendation()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message ?: ""
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.d("MachineLearningRepository", "getSleepRecommendation: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getChronotype(): LiveData<Result<ChronotypeResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.chronotype()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message ?: ""
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            Log.d("MachineLearningRepository", "getSleepRecommendation: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
}
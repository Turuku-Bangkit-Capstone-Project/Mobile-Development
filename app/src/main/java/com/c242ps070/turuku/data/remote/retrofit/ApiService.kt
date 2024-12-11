package com.c242ps070.turuku.data.remote.retrofit

import com.c242ps070.turuku.data.remote.request.HistoryRequest
import com.c242ps070.turuku.data.remote.request.LoginRequest
import com.c242ps070.turuku.data.remote.request.RegisterRequest
import com.c242ps070.turuku.data.remote.response.ChronotypeResponse
import com.c242ps070.turuku.data.remote.response.HistoryResponse
import com.c242ps070.turuku.data.remote.response.LoginResponse
import com.c242ps070.turuku.data.remote.response.RefreshTokenResponse
import com.c242ps070.turuku.data.remote.response.SleepRecommendationResponse
import com.c242ps070.turuku.data.remote.response.SuccessResponse
import com.c242ps070.turuku.data.remote.request.UpsertUserDataRequest
import com.c242ps070.turuku.data.remote.response.UserDataResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): SuccessResponse

    @DELETE("logout")
    suspend fun logout()

    @GET("token")
    suspend fun refreshToken(): RefreshTokenResponse

    @GET("userdata")
    suspend fun getUserData(): UserDataResponse

    @POST("userdata")
    suspend fun upsertUserData(
        @Body request: UpsertUserDataRequest
    ): SuccessResponse

    @GET("history")
    suspend fun getHistory(): List<HistoryResponse>

    @POST("history")
    suspend fun addHistory(
        @Body request: HistoryRequest
    ): SuccessResponse

    @POST("chronotype")
    suspend fun chronotype(): ChronotypeResponse

    @POST("sleeprecomendation")
    suspend fun sleepRecommendation(): SleepRecommendationResponse
}
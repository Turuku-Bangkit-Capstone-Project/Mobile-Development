package com.c242ps070.turuku.data.remote.retrofit

import com.c242ps070.turuku.data.remote.request.LoginRequest
import com.c242ps070.turuku.data.remote.request.RegisterRequest
import com.c242ps070.turuku.data.remote.response.LoginResponse
import com.c242ps070.turuku.data.remote.response.RegisterResponse
import com.c242ps070.turuku.data.remote.response.UserResponse
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
    ): RegisterResponse

    @DELETE("logout")
    suspend fun logout()

    @GET("token")
    suspend fun refreshToken(): LoginResponse

    @GET("users")
    suspend fun getUsers(): UserResponse
}
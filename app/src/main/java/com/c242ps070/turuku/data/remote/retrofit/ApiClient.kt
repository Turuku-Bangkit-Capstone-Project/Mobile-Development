package com.c242ps070.turuku.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://prod-turuku-800638181621.asia-southeast2.run.app/api/v1/"
    private const val ML_BASE_URL = "https://turuku-ml-api-800638181621.asia-southeast2.run.app/"

    fun create(token: String): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(token))
            .build()
            .create(ApiService::class.java)
    }

    fun createForML(token: String): MLApiService {
        return Retrofit.Builder()
            .baseUrl(ML_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(token))
            .build()
            .create(MLApiService::class.java)
    }

    private fun okHttpClient(token: String): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(authInterceptor)
            .build()
    }
}
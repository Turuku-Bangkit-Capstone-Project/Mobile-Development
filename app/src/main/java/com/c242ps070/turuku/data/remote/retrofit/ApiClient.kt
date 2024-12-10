package com.c242ps070.turuku.data.remote.retrofit

import com.c242ps070.turuku.data.local.datastore.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://prod-turuku-800638181621.asia-southeast2.run.app/api/v1/"

    fun create(userPreference: UserPreference): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(userPreference))
            .build()
            .create(ApiService::class.java)
    }

    private fun okHttpClient(userPreference: UserPreference): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val token = runBlocking { userPreference.getUser().first().token }
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
        val cookieInterceptor = Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
                var refreshToken: String? = null
                for (header in originalResponse.headers("Set-Cookie")) {
                    if (header.contains("refreshToken")) {
                        refreshToken = header
                        break
                    }
                }

                if (refreshToken != null) {
                    runBlocking { userPreference.saveRefreshToken(refreshToken) }
                }
            }
            originalResponse
        }
        val addCookieInterceptor = Interceptor { chain ->
            val builder = chain.request().newBuilder()
            val refreshToken = runBlocking { userPreference.refreshToken.first() }
            if (refreshToken != null) {
                builder.addHeader("Cookie", refreshToken)
            }
            chain.proceed(builder.build())
        }
        return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(authInterceptor)
            .addInterceptor(cookieInterceptor)
            .addInterceptor(addCookieInterceptor)
            .build()
    }
}
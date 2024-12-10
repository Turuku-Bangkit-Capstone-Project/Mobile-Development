package com.c242ps070.turuku.data.di

import android.content.Context
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.dataStore
import com.c242ps070.turuku.data.remote.retrofit.ApiClient
import com.c242ps070.turuku.data.repository.AuthRepository
import com.c242ps070.turuku.data.repository.MachineLearningRepository
import com.c242ps070.turuku.data.repository.UserRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiClient.create(userPreference)
        return AuthRepository(apiService)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiClient.create(userPreference)
        return UserRepository(apiService)
    }

    fun provideMLRepository(context: Context): MachineLearningRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiClient.create(userPreference)
        return MachineLearningRepository(apiService)
    }
}
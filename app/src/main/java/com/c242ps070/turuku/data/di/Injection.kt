package com.c242ps070.turuku.data.di

import android.content.Context
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.dataStore
import com.c242ps070.turuku.data.remote.retrofit.ApiClient
import com.c242ps070.turuku.data.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiClient.create(
            runBlocking { userPreference.getUser().first().token }
        )
        return AuthRepository(apiService)
    }
}
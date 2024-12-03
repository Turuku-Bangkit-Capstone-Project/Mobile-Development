package com.c242ps070.turuku.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.data.di.Injection
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.dataStore
import com.c242ps070.turuku.data.repository.AuthRepository
import com.c242ps070.turuku.viewmodel.LoginViewModel

class ViewModelFactory(
    private val authRepository: AuthRepository? = null,
    private val userPreference: UserPreference? = null
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return authRepository?.let { LoginViewModel(it) } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideAuthRepository(context),
                        UserPreference.getInstance(context.dataStore)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }

        @JvmStatic
        fun clearInstance() {
            INSTANCE = null
        }
    }
}
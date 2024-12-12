package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.repository.AuthRepository
import com.c242ps070.turuku.data.repository.HistoryRepository
import com.c242ps070.turuku.utils.AppAuthState
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val authRepository: AuthRepository,
    private val historyRepository: HistoryRepository,
    private val userPreference: UserPreference
): ViewModel() {
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun clearLocalData() {
        viewModelScope.launch {
            userPreference.delete()
            userPreference.saveAppAuthState(AppAuthState.IS_NOT_LOGGED_IN)
            historyRepository.deleteAllSleepHistoryRoom()
        }
    }
}
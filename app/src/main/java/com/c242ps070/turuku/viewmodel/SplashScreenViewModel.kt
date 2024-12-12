package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.utils.AppAuthState

class SplashScreenViewModel(
    userPreference: UserPreference
): ViewModel() {
    val appAuthState: LiveData<AppAuthState> = userPreference.appAuthState.asLiveData()
}
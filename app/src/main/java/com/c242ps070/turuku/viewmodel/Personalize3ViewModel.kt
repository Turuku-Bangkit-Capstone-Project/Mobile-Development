package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import kotlinx.coroutines.launch

class Personalize3ViewModel(
    private val userPreference: UserPreference
): ViewModel() {
    private val _bedTime = MutableLiveData<String>()
    val bedTime: LiveData<String> = _bedTime

    fun setBedTime(bedTime: String) {
        _bedTime.value = bedTime
    }

    fun getUserLoggedIn(): LiveData<UserPreferenceModel> = userPreference.getUser().asLiveData()

    fun saveBedTime(bedTime: String) {
        viewModelScope.launch {
            userPreference.saveBedTime(bedTime)
        }
    }
}
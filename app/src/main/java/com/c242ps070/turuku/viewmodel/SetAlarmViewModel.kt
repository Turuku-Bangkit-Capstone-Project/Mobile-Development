package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.local.datastore.UserPreference
import kotlinx.coroutines.launch

class SetAlarmViewModel(
    private val userPreference: UserPreference
): ViewModel() {
    private val _wakeupTime = MutableLiveData<String>()
    val wakeupTime: LiveData<String> = _wakeupTime

    fun setWakeupTime(wakeupTime: String) {
        _wakeupTime.value = wakeupTime
    }

    fun saveWakeupTime(wakeupTime: String) {
        viewModelScope.launch {
            userPreference.saveWakeupTime(wakeupTime)
        }
    }
}
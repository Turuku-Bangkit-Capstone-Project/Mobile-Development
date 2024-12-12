package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.local.datastore.AlarmPreference
import com.c242ps070.turuku.data.local.room.entity.SleepHistoryEntity
import com.c242ps070.turuku.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class SetAlarmViewModel(
    private val historyRepository: HistoryRepository,
    private val alarmPreference: AlarmPreference
): ViewModel() {
    private val _wakeupTime = MutableLiveData<String>()
    val wakeupTime: LiveData<String> = _wakeupTime

    fun setWakeupTime(wakeupTime: String) {
        _wakeupTime.value = wakeupTime
    }

    fun getLastHistoryRoom(): LiveData<SleepHistoryEntity?> =
        historyRepository.getLastSleepHistoryRoom()

    fun saveAlarmTime(wakeupTime: Long, bedTime: Long) {
        viewModelScope.launch {
            alarmPreference.saveAlarmTime(wakeupTime, bedTime)
        }
    }
}
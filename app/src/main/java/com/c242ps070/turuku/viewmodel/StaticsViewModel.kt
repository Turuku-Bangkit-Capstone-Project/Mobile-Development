package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c242ps070.turuku.data.local.room.entity.SleepHistoryEntity
import com.c242ps070.turuku.data.repository.HistoryRepository
import java.util.Date

class StaticsViewModel(
    private val historyRepository: HistoryRepository
): ViewModel() {
    private val _startDate: MutableLiveData<Date> = MutableLiveData<Date>()
    val startDate: MutableLiveData<Date> get() = _startDate

    private val _endDate: MutableLiveData<Date> = MutableLiveData<Date>()
    val endDate: MutableLiveData<Date> get() = _endDate

    fun setStartDate(date: Date) {
        _startDate.value = date
    }

    fun setEndDate(date: Date) {
        _endDate.value = date
    }

    fun getHistoryInRange(): LiveData<List<SleepHistoryEntity>> =
        historyRepository.getSleepHistoryInRange(_startDate.value!!, _endDate.value!!)
}
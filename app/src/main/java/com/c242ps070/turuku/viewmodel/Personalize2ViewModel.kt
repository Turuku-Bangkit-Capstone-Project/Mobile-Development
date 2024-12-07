package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import kotlinx.coroutines.launch

class Personalize2ViewModel(
    private val userPreference: UserPreference
): ViewModel() {
    fun getUserLoggedIn(): LiveData<UserPreferenceModel> = userPreference.getUser().asLiveData()

    fun saveAgeAndGender(age: Int, gender: String) {
        viewModelScope.launch {
            userPreference.saveAgeAndGender(age, gender)
        }
    }
}
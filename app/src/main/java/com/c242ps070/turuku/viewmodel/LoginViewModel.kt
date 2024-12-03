package com.c242ps070.turuku.viewmodel

import androidx.lifecycle.ViewModel
import com.c242ps070.turuku.data.repository.AuthRepository

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
}
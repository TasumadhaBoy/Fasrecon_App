package com.application.fasrecon.ui.splashscreen

import androidx.lifecycle.ViewModel
import com.application.fasrecon.data.repository.AuthRepository

class SplashScreenViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun getSession(): Boolean = authRepository.getSession()
}
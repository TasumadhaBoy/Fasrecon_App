package com.application.fasrecon.ui.profile

import androidx.lifecycle.ViewModel
import com.application.fasrecon.data.repository.UserRepository


class ProfileViewModel (private val userRepository: UserRepository): ViewModel() {

    fun getUserData() = userRepository.getUserData()

    suspend fun logout() {
        userRepository.logout()
    }
}
package com.application.fasrecon.ui.home

import androidx.lifecycle.ViewModel
import com.application.fasrecon.data.repository.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserData() = userRepository.getUserData()

    fun getClothesTotal(): Int = userRepository.get
}
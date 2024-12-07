package com.application.fasrecon.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.local.entity.UserEntity
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.util.WrapMessage
import kotlinx.coroutines.launch

class ProfileViewModel (private val userRepository: UserRepository): ViewModel() {

    fun getUserData() = userRepository.getUserData()

    suspend fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
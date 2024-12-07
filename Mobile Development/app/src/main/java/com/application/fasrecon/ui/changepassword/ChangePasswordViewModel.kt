package com.application.fasrecon.ui.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.util.WrapMessage

class ChangePasswordViewModel (private val userRepository: UserRepository): ViewModel() {
    private val changeMessage = MutableLiveData<String?>()
    val changePasswordMessage: LiveData<String?> = changeMessage

    private val loadData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = loadData

    private val error = MutableLiveData<WrapMessage<String?>>()
    val errorHandling: LiveData<WrapMessage<String?>> = error

    fun getUserData() = userRepository.getUserData()

    fun changePassword(newPassword: String) {
        userRepository.changePassword(newPassword).observeForever { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> loadData.value = true
                    is Result.Success -> {
                        loadData.value = false
                       changeMessage.value = result.data
                    }
                    is Result.Error -> {
                        loadData.value = false
                        error.value = result.errorMessage
                    }
                }
            }
        }
    }
}
package com.application.fasrecon.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.model.UserModel
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.util.WrapMessage

class ProfileViewModel (private val userRepository: UserRepository): ViewModel() {

    private val _userData = MutableLiveData<UserModel>()
    val userData: LiveData<UserModel> = _userData

    private val error = MutableLiveData<WrapMessage<String?>>()
    val errorHandling: LiveData<WrapMessage<String?>> = error

    fun getUserData() {
        userRepository.getUserData().observeForever { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        _userData.value = result.data
                    }

                    is Result.Error -> {
                        error.value = result.errorMessage
                    }
                }
            }
        }
    }
}
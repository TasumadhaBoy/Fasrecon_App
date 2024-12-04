package com.application.fasrecon.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.repository.AuthRepository
import com.application.fasrecon.util.WrapMessage

class RegisterViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val regMessage = MutableLiveData<String?>()
    val registerMessage: LiveData<String?> = regMessage

    private val loadData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = loadData

    private val error = MutableLiveData<WrapMessage<String?>>()
    val errorHandling: LiveData<WrapMessage<String?>> = error

    fun registerAccount(username: String, email: String, password: String) {
        authRepository.registerAccount(username, email, password).observeForever { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> loadData.value = true
                    is Result.Success -> {
                        loadData.value = false
                        regMessage.value = result.data
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
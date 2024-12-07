package com.application.fasrecon.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.repository.AuthRepository
import com.application.fasrecon.util.WrapMessage
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val logMessage = MutableLiveData<String?>()
    val loginMessage: LiveData<String?> = logMessage

    private val loadData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = loadData

    private val error = MutableLiveData<WrapMessage<String?>>()
    val errorHandling: LiveData<WrapMessage<String?>> = error

    fun loginAccount(email: String, password: String) {
        authRepository.loginAccount(email, password).observeForever { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> loadData.value = true
                    is Result.Success -> {
                        loadData.value = false
                        logMessage.value = result.data
                    }
                    is Result.Error -> {
                        loadData.value = false
                        error.value = result.errorMessage
                    }
                }
            }
        }
    }

    fun saveSession(isLogin: Boolean, password: String) {
        viewModelScope.launch {
            authRepository.saveSession(isLogin, password)
        }
    }
}
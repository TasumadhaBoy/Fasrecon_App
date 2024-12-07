package com.application.fasrecon.ui.profilesettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.local.entity.UserEntity
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.util.WrapMessage
import kotlinx.coroutines.launch

class ProfileSettingsViewModel (private val userRepository: UserRepository): ViewModel() {

    private val updateMsg = MutableLiveData<String?>()
    val updateDataMessage: LiveData<String?> = updateMsg

    private val errorUpdate = MutableLiveData<WrapMessage<String?>>()
    val errorUpdateHandling: LiveData<WrapMessage<String?>> = errorUpdate

    private val loadData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = loadData

    fun getUserData() = userRepository.getUserData()

    fun updateData(name: String, photo: String? = null) {
        userRepository.updateUserData(name, photo).observeForever { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> loadData.value = true
                    is Result.Success -> {
                        loadData.value = false
                        updateMsg.value = result.data
                    }

                    is Result.Error -> {
                        loadData.value = false
                        errorUpdate.value = result.errorMessage
                    }
                }
            }
        }
    }

    fun updateUserDataLocal(name: String, photo: String) {
        viewModelScope.launch {
            userRepository.updateUserDataLocal(name, photo)
        }
    }
}
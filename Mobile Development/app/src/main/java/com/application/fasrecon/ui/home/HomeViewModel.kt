package com.application.fasrecon.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.remote.response.Label
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.util.WrapMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserData() = userRepository.getUserData()

    fun getClothesTotal(): LiveData<Int> = userRepository.getClothesTotal()


    private val classify = MutableLiveData<Label>()
    val classifyResult: LiveData<Label> = classify

    private val loadData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = loadData

    private val error = MutableLiveData<WrapMessage<String?>>()
    val errorHandling: LiveData<WrapMessage<String?>> = error

    fun classifyImage(multipartBody: MultipartBody.Part) {
        userRepository.classifyImage(multipartBody).observeForever { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> loadData.value = true
                    is Result.Success -> {
                        loadData.value = false
                        classify.value = result.data
                    }

                    is Result.Error -> {
                        loadData.value = false
                        error.value = result.errorMessage
                    }
                }
            }
        }
    }

    fun insertClothes(clothes: String, type: String?, color: String?){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertClothesData(clothes, type, color)
        }
    }
}
package com.application.fasrecon.ui.myclothes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.util.WrapMessage
import okhttp3.MultipartBody

class MyClothesViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val classify = MutableLiveData<String?>()
    val classifyResult: LiveData<String?> = classify

    private val loadData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = loadData

    private val error = MutableLiveData<WrapMessage<String?>>()
    val errorHandling: LiveData<WrapMessage<String?>> = error

    fun classifyImage(multipartBody: MultipartBody.Part) {
//        userRepository.classifyImage(multipartBody).observeForever { result ->
//            if (result != null) {
//                when (result) {
//                    is Result.Loading -> loadData.value = true
//                    is Result.Success -> {
//                        loadData.value = false
//                        classify.value = result.data
//                    }
//
//                    is Result.Error -> {
//                        loadData.value = false
//                        error.value = result.errorMessage
//                    }
//                }
//            }
//        }
    }
}
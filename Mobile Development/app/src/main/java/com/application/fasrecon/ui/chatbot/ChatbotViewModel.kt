package com.application.fasrecon.ui.chatbot

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.fasrecon.data.local.entity.MessageEntity
import com.application.fasrecon.data.remote.response.RecommendationResponse
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.util.WrapMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.local.entity.ClothesEntity

class ChatbotViewModel (private val userRepository: UserRepository): ViewModel() {
    private val response = MutableLiveData<RecommendationResponse>()
    val chatbotResponse: LiveData<RecommendationResponse> = response

    private val loadData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = loadData

    private val error = MutableLiveData<WrapMessage<String?>>()
    val errorHandling: LiveData<WrapMessage<String?>> = error

    fun chatbot(text: String) {
        userRepository.generateText(text).observeForever { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> loadData.value = true
                    is Result.Success -> {
                        loadData.value = false
                        response.value = result.data
                    }

                    is Result.Error -> {
                        loadData.value = false
                        error.value = result.errorMessage
                    }
                }
            }
        }
    }

    fun insertMessageFirst(id:String, type: String, photo: String, time: String, firstMessage: String, listPhotos: List<String> = emptyList()){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertMessageFirst(id, type, photo, time, firstMessage, listPhotos)
        }
    }

    fun insertMessage(id:String, type: String, message: String, photo: String, first: Boolean, listPhotos: List<String>){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertMessage(id, type, message, photo, first, listPhotos)
        }
    }

    fun getAllHistoryMessage(id: String): LiveData<List<MessageEntity>> {
        return userRepository.getAllHistoryMessage(id)
    }

    fun deleteChat(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteChat(id)
        }
    }

    fun getUserData() = userRepository.getUserData()
    fun getAllClothesByType(type: String): LiveData<List<ClothesEntity>> = userRepository.getAllClothesByType(type)

}
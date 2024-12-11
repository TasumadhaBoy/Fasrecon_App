package com.application.fasrecon.ui.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.fasrecon.data.local.entity.MessageEntity
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.util.WrapMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatbotViewModel (private val userRepository: UserRepository): ViewModel() {
    private val response = MutableLiveData<String>()
    val chatbotResponse: LiveData<String> = response

    private val loadData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = loadData

    private val error = MutableLiveData<WrapMessage<String?>>()
    val errorHandling: LiveData<WrapMessage<String?>> = error

//    fun chatbot(message: String) {
//        userRepository.(message).observeForever { result ->
//            if (result != null) {
//                when (result) {
//                    is Result.Loading -> loadData.value = true
//                    is Result.Success -> {
//                        loadData.value = false
//                        result.value = result.data
//                    }
//
//                    is Result.Error -> {
//                        loadData.value = false
//                        error.value = result.errorMessage
//                    }
//                }
//            }
//        }
//    }

    fun insertChat(time: String, firstMessage: String){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertChat(time, firstMessage)
        }
    }

    fun insertMessage(type: String, message: String){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertMessage(type, message)
        }
    }

    fun getAllHistoryMessage(): LiveData<List<MessageEntity>> = userRepository.getAllHistoryMessage()
}
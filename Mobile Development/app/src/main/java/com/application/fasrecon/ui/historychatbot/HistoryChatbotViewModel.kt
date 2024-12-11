package com.application.fasrecon.ui.historychatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.fasrecon.data.local.entity.ChatEntity
import com.application.fasrecon.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryChatbotViewModel (private val userRepository: UserRepository) : ViewModel() {

    fun getAllHistoryChat(): LiveData<List<ChatEntity>> = userRepository.getAllHistoryChat()
    fun deleteChat(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteChat(id)
        }
    }
}
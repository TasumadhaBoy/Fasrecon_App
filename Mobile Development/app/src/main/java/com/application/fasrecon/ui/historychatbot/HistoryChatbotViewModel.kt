package com.application.fasrecon.ui.historychatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.application.fasrecon.data.local.entity.ChatEntity
import com.application.fasrecon.data.repository.UserRepository

class HistoryChatbotViewModel (private val userRepository: UserRepository) : ViewModel() {

    fun getAllHistoryChat(): LiveData<List<ChatEntity>> = userRepository.getAllHistoryChat()
}
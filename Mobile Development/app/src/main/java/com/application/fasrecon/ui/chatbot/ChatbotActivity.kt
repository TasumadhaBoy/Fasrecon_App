package com.application.fasrecon.ui.chatbot

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.fasrecon.data.model.ChatMessage
import com.application.fasrecon.databinding.ActivityChatbotBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var adapter: ChatbotMessageAdapter
    private val messagesList = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        adapter = ChatbotMessageAdapter()
        binding.listMessage.layoutManager = LinearLayoutManager(this)
        binding.listMessage.adapter = adapter

        binding.sendMessageButton.setOnClickListener {
            val messageText = binding.messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
                binding.messageInput.text?.clear()
            }
        }
    }

    private fun sendMessage(text: String) {
        val userMessage = ChatMessage(id = "SEND", messages = text, time = getCurrentTime())
        messagesList.add(userMessage)

        adapter.submitList(messagesList.toList())
        binding.listMessage.scrollToPosition(messagesList.size - 1)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun receiveMessage(text: String) {
        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        binding.listMessage.postDelayed({
            val botMessage = ChatMessage(id = "GET", messages = text, time = currentTime)
            messagesList.add(botMessage)

            adapter.submitList(messagesList.toList())
            binding.listMessage.scrollToPosition(messagesList.size - 1)
        }, 1000)
    }

    private fun setActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}


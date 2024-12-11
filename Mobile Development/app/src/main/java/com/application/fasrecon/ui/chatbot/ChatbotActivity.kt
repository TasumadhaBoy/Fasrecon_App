package com.application.fasrecon.ui.chatbot

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.fasrecon.R
import com.application.fasrecon.data.model.ChatMessage
import com.application.fasrecon.databinding.ActivityChatbotBinding
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryUser
import com.application.fasrecon.util.setKeyboardMargin
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var adapter: ChatbotMessageAdapter
    private val messagesList = mutableListOf<ChatMessage>()
    private val chatbotViewModel: ChatbotViewModel by viewModels { ViewModelFactoryUser.getInstance(this) }
    private var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            setKeyboardMargin(binding.root, binding.typeMessageContainer)
        }

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun sendMessage(text: String) {
        val currentTime = getCurrentTime()
        val userMessage = ChatMessage(id = "SEND", messages = text)
        messagesList.add(userMessage)
        adapter.submitList(messagesList.toList())
        if (!check) {
            chatbotViewModel.insertChat(currentTime, text)
            check = true
        }
        chatbotViewModel.insertMessage("SEND", text)
        binding.listMessage.scrollToPosition(messagesList.size - 1)

        receiveMessage(text + "Mantap")
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String =
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


    private fun receiveMessage(text: String) {
        binding.listMessage.postDelayed({
            val botMessage = ChatMessage(id = "GET", messages = text)
            messagesList.add(botMessage)

            adapter.submitList(messagesList.toList())
            chatbotViewModel.insertMessage("GET", text)
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


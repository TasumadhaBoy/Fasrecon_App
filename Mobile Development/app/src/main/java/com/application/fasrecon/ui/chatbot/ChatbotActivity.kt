package com.application.fasrecon.ui.chatbot

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.R
import com.application.fasrecon.data.local.entity.MessageEntity
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
    private val chatbotViewModel: ChatbotViewModel by viewModels {
        ViewModelFactoryUser.getInstance(
            this
        )
    }
    private var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        setDataFirst()

        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            setKeyboardMargin(binding.root, binding.typeMessageContainer)
            setKeyboardMargin(binding.root, binding.listMessage)
        }

        chatbotViewModel.errorHandling.observe(this) {
            it.getDataIfNotDisplayed()?.let { msg ->
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Chatbot can't provide recommendation")
                    .setConfirmText(getString(R.string.try_again))
                    .setContentText(msg)
                    .show()
            }
        }

        adapter = ChatbotMessageAdapter()
        binding.listMessage.layoutManager = LinearLayoutManager(this)
        binding.listMessage.adapter = adapter

        chatbotViewModel.loadingData.observe(this) {
            displayLoading(it)
        }

        val chat = if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra(CHAT)
        } else {
            intent.getStringExtra(CHAT)
        }

        if (chat != null) {
            binding.typeMessageContainer.visibility = View.GONE
        }

        chatbotViewModel.getUserData().observe(this) { user ->
            chatbotViewModel.chatbotResponse.observe(this) { recommendationResponse ->
                recommendationResponse.recommendationText?.let { recomText ->
                    receiveMessage(
                        recomText,
                        user.id
                    )
                }
                recommendationResponse.outfitRecommendations?.recommendedOutfits?.let { types ->
                    types.forEach { itemTypes ->
                        val newClothes = mutableListOf<String>()
                        itemTypes?.let {
                            chatbotViewModel.getAllClothesByType(it).observe(this) { listClothes ->
                                listClothes.forEach { clothes ->
                                    newClothes.add(clothes.clothesImage)
                                }
                                Log.d("Mantap", newClothes.toString())
                                if (newClothes.isNotEmpty()) {
                                    getMessageWithPhoto("", user.id, newClothes)
                                }
                            }
                        }
                    }
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setDataFirst() {
        val chat = if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra(CHAT)
        } else {
            intent.getStringExtra(CHAT)
        }

        if (chat != null) {
            check = true
            chatbotViewModel.getAllHistoryMessage(chat).observe(this) {
                setAllMessage(it)
            }
        }

        binding.sendMessageButton.setOnClickListener {
            val messageText = binding.messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
                binding.messageInput.text?.clear()
            }
        }
    }

    private fun sendMessage(text: String) {
        val currentTime = getCurrentTime()
        chatbotViewModel.getUserData().observe(this) {
            val userMessage = ChatMessage(id = "SEND", messages = text, it.photoUrl.toString())
            messagesList.add(userMessage)

            adapter.submitList(messagesList.toList())

            if (!check) {
                check = true
                binding.timeInfo.text = currentTime
                chatbotViewModel.insertMessageFirst(
                    it.id + "chathistory",
                    "SEND",
                    it.photoUrl.toString(),
                    currentTime,
                    text
                )
            } else {
                chatbotViewModel.insertMessage(
                    it.id + "chathistory",
                    "SEND",
                    text,
                    it.photoUrl.toString(),
                    true,
                    emptyList()
                )
            }

            binding.listMessage.scrollToPosition(messagesList.size - 1)
            chatbotViewModel.chatbot(text)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String =
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    private fun receiveMessage(text: String, idUser: String) {
        val botMessage = ChatMessage(id = "GET", messages = text, "")
        messagesList.add(botMessage)
        adapter.submitList(messagesList.toList())
        chatbotViewModel.insertMessage(idUser + "chathistory", "GET", text, "", true, emptyList())
        binding.listMessage.scrollToPosition(messagesList.size - 1)
    }

    private fun getMessageWithPhoto(text: String, idUser: String, listPhotos: List<String>) {
        val botMessage = ChatMessage(id = "GET_PHOTO", messages = text, "", listPhotos)
        messagesList.add(botMessage)

        adapter.submitList(messagesList.toList())
        chatbotViewModel.insertMessage(idUser + "chathistory", "GET_PHOTO", text, "", true, listPhotos)

        binding.listMessage.scrollToPosition(messagesList.size - 1)
    }

    private fun setActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setAllMessage(listMessage: List<MessageEntity>) {
        listMessage.forEach { messageItem ->
            messagesList.add(
                ChatMessage(
                    messageItem.messageType,
                    messageItem.message,
                    messageItem.photoProfile,
                    messageItem.listPhoto ?: emptyList()
                )
            )
        }
        adapter.submitList(messagesList.toList())
    }

    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingChatbot.visibility = View.VISIBLE
        } else {
            binding.loadingChatbot.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu_chatbot, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.delete_chat -> {
                val chat = if (Build.VERSION.SDK_INT >= 33) {
                    intent.getStringExtra(CHAT)
                } else {
                    intent.getStringExtra(CHAT)
                }

                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.delete_chat))
                    .setContentText(getString(R.string.confirmation_delete))
                    .setConfirmText(getString(R.string.yes))
                    .setCancelText(getString(R.string.no))
                    .setConfirmClickListener { sDialog ->
                        chat?.let { chatbotViewModel.deleteChat(chat) }
                        finish()
                        sDialog.dismissWithAnimation()
                    }
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    companion object {
        const val CHAT = "chat"
    }
}


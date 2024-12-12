package com.application.fasrecon.ui.chatbot

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.fasrecon.R
import com.application.fasrecon.data.model.ChatMessage
import com.application.fasrecon.databinding.ItemChatbotMessageBinding

class ChatbotMessageAdapter: ListAdapter<ChatMessage, ChatbotMessageAdapter.ChatbotViewHolder>(DIFF_CALLBACK) {
    var messages = mutableListOf<ChatMessage>()

    inner class ChatbotViewHolder(val binding: ItemChatbotMessageBinding): ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val currentList = currentList.toMutableList()
                currentList.removeAt(adapterPosition)
                submitList(currentList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatbotViewHolder {
        val binding = ItemChatbotMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatbotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatbotViewHolder, position: Int) {
        val item = getItem(position)
        when (item.id) {
            "SEND" -> {
                holder.binding.userMessage.apply {
                    text = item.messages
                    visibility = View.VISIBLE
                }
                holder.binding.chatbotMessage.visibility = View.GONE
                holder.binding.clothesImageRecommendation.visibility = View.GONE
            }
            "GET" -> {
                holder.binding.chatbotMessage.apply {
                    text = item.messages
                    visibility = View.VISIBLE
                }
                holder.binding.userMessage.visibility = View.GONE
                holder.binding.clothesImageRecommendation.visibility = View.GONE
            }
            "GET_PHOTO" -> {
                holder.binding.chatbotMessage.visibility = View.GONE
                holder.binding.userMessage.visibility = View.GONE
                if (item.listPhoto.isEmpty()) {
                    Log.d("list photo", item.listPhoto.toString())
                    holder.binding.clothesImageRecommendation.visibility = View.GONE
                } else {
                    holder.binding.listImage.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    val adapter = ClothesRecommendationAdapter()
                    adapter.submitList(item.listPhoto)
                    holder.binding.listImage.adapter = adapter
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ChatMessage> =
            object : DiffUtil.ItemCallback<ChatMessage>() {
                override fun areItemsTheSame(
                    oldItem: ChatMessage,
                    newItem: ChatMessage
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: ChatMessage,
                    newItem: ChatMessage
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
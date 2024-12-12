package com.application.fasrecon.ui.historychatbot

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.fasrecon.data.local.entity.ChatEntity
import com.application.fasrecon.databinding.ItemChatbotHistoryBinding
import com.application.fasrecon.ui.chatbot.ChatbotActivity

class HistoryChatbotAdapter: ListAdapter<ChatEntity, HistoryChatbotAdapter.ListViewHolder>(
    DIFF_CALLBACK) {

    inner class ListViewHolder (private val binding: ItemChatbotHistoryBinding): ViewHolder(binding.root) {
        fun bind(chatData: ChatEntity) {
            binding.chatTitle.text = chatData.chatTitle
            binding.chatTime.text = chatData.chatTime
            binding.chatMessage.text = chatData.firstMessage

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatbotActivity::class.java)
                intent.putExtra(ChatbotActivity.CHAT, chatData.id)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemChatbotHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ChatEntity> =
            object : DiffUtil.ItemCallback<ChatEntity>(){
                override fun areItemsTheSame(oldItem: ChatEntity, newItem: ChatEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ChatEntity, newItem: ChatEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
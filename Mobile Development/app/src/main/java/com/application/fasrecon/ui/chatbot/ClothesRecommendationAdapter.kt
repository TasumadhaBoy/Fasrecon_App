package com.application.fasrecon.ui.chatbot

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.fasrecon.data.local.entity.ClothesEntity
import com.application.fasrecon.databinding.ItemClothesListBinding
import com.application.fasrecon.databinding.ItemClothesRecommendationBinding
import com.bumptech.glide.Glide

class ClothesRecommendationAdapter: ListAdapter<String, ClothesRecommendationAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder (private val binding: ItemClothesRecommendationBinding): ViewHolder(binding.root) {
        fun bind(clothesImage: String) {
            Glide.with(itemView)
                .load(clothesImage)
                .into(binding.clothesImage)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemClothesRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>(){
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
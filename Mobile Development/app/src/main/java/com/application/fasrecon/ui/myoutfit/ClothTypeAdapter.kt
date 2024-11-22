package com.application.fasrecon.ui.myoutfit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.fasrecon.databinding.ItemClothTypeBinding

class ClothTypeAdapter: ListAdapter<String, ClothTypeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private var radioChecked = -1

    inner class ListViewHolder (private val binding: ItemClothTypeBinding): ViewHolder(binding.root) {
        fun bind(type: String, pos: Int) {
            binding.tvClothType.text = type
            binding.radioBtn.isChecked = pos == radioChecked

            itemView.setOnClickListener {
                radioCheckedInfo(itemView)
            }
            binding.radioBtn.setOnClickListener {
                radioCheckedInfo(binding.radioBtn)
            }
        }

        private fun radioCheckedInfo(view: View) {
            val previousRadioChecked = radioChecked
            radioChecked = adapterPosition
            notifyItemChanged(previousRadioChecked)
            notifyItemChanged(radioChecked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemClothTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position), position)
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
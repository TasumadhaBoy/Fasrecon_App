package com.application.fasrecon.ui.myclothes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.fasrecon.databinding.ItemClothesTypeBinding

class MyClothesTypeAdapter: ListAdapter<String, MyClothesTypeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private var clothList: List<String> = emptyList()
    private var radioChecked = -1

    inner class ListViewHolder (private val binding: ItemClothesTypeBinding): ViewHolder(binding.root) {
        fun bind(type: String, pos: Int) {
            binding.tvClothesType.text = type
            binding.radioBtn.isChecked = pos == radioChecked
            itemView.setOnClickListener {
                radioCheckedInfo()
            }
            binding.radioBtn.setOnClickListener {
                radioCheckedInfo()
            }
        }

        private fun radioCheckedInfo() {
            val previousRadioChecked = radioChecked
            radioChecked = adapterPosition
            notifyItemChanged(previousRadioChecked)
            notifyItemChanged(radioChecked)
        }
    }

    fun getSelectedItem(): String? {
        return if (radioChecked >= 0) getItem(radioChecked) else null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemClothesTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    interface OnItemClickListener {
        fun onItemClick(item: String, position: Int)
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
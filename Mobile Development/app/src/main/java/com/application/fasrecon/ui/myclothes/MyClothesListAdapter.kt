package com.application.fasrecon.ui.myclothes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.fasrecon.data.local.entity.ClothesEntity
import com.application.fasrecon.databinding.ItemClothesListBinding
import com.bumptech.glide.Glide

class MyClothesListAdapter (private val deleteClothes: (ClothesEntity) -> Unit): ListAdapter<ClothesEntity, MyClothesListAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder (private val binding: ItemClothesListBinding): ViewHolder(binding.root) {
        fun bind(clothesData: ClothesEntity) {
            binding.clothesName.text = clothesData.clothesName

            clothesData.type?.let {
                binding.cardLabelClothes1.alpha = 1f
                binding.label1.text = it
            }

            clothesData.color?.let {
                binding.cardLabelClothes2.alpha = 1f
                binding.label2.text = it
            }

            Glide.with(itemView)
                .load(clothesData.clothesImage)
                .into(binding.clothesImage)

            binding.deleteClothes.setOnClickListener {
                deleteClothes(clothesData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemClothesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ClothesEntity> =
            object : DiffUtil.ItemCallback<ClothesEntity>(){
                override fun areItemsTheSame(oldItem: ClothesEntity, newItem: ClothesEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ClothesEntity, newItem: ClothesEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}

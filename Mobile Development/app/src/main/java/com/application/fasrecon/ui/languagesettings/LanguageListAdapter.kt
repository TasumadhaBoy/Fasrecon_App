package com.application.fasrecon.ui.languagesettings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.fasrecon.data.preferences.LanguageModel
import com.application.fasrecon.databinding.ItemLanguageSettingsBinding

class LanguageListAdapter :
    ListAdapter<LanguageModel, LanguageListAdapter.LanguageViewHolder>(DIFF_CALLBACK) {
    class LanguageViewHolder(private val binding: ItemLanguageSettingsBinding) :
        ViewHolder(binding.root) {
        fun bind(language: LanguageModel) {
            binding.countryImage.setImageDrawable(language.countryImage)
            binding.countryName.text = language.countryName

            if (language.checked) {
                binding.languageChecked.visibility = View.VISIBLE
            } else {
                binding.languageChecked.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LanguageViewHolder {
        val binding =
            ItemLanguageSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<LanguageModel> =
            object : DiffUtil.ItemCallback<LanguageModel>() {
                override fun areItemsTheSame(
                    oldItem: LanguageModel,
                    newItem: LanguageModel
                ): Boolean {
                    return oldItem.countryName == newItem.countryName
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: LanguageModel,
                    newItem: LanguageModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
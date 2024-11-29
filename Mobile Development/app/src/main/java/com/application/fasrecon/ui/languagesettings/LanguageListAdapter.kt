package com.application.fasrecon.ui.languagesettings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.fasrecon.R
import com.application.fasrecon.data.model.LanguageModel
import com.application.fasrecon.databinding.ItemLanguageSettingsBinding

class LanguageListAdapter :
    ListAdapter<LanguageModel, LanguageListAdapter.LanguageViewHolder>(DIFF_CALLBACK) {

        private var languageChecked = -1

    inner class LanguageViewHolder(private val binding: ItemLanguageSettingsBinding) :
        ViewHolder(binding.root) {
        fun bind(language: LanguageModel, pos: Int) {
            binding.countryImage.setImageDrawable(language.countryImage)
            binding.countryName.text = language.countryName

            if (pos == languageChecked || language.checked) {
                languageChecked = pos
                binding.languageChecked.visibility = View.VISIBLE
                binding.languageContainer.setBackgroundResource(R.drawable.custom_language_settings_checked)
            } else {
                binding.languageChecked.visibility = View.GONE
                binding.languageContainer.setBackgroundResource(R.drawable.custom_language_settings_unchecked)
            }

            itemView.setOnClickListener {
                languageCheckedInfo()
            }
        }

        private fun languageCheckedInfo() {
            val previousLanguageChecked = languageChecked
            languageChecked = adapterPosition

            val previousItem = getItem(previousLanguageChecked)
            if (previousItem != null) previousItem.checked = false

            notifyItemChanged(previousLanguageChecked)
            notifyItemChanged(languageChecked)
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
        holder.bind(getItem(position), position)
    }

    fun getSelectedItem(): LanguageModel? {
        return if (languageChecked >= 0) getItem(languageChecked) else null
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<LanguageModel> =
            object : DiffUtil.ItemCallback<LanguageModel>() {
                override fun areItemsTheSame(
                    oldItem: LanguageModel,
                    newItem: LanguageModel
                ): Boolean {
                    return oldItem.id == newItem.id
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
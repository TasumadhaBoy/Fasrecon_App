package com.application.fasrecon.ui.languagesettings

import android.content.Context
import androidx.core.content.ContextCompat
import com.application.fasrecon.R
import com.application.fasrecon.data.preferences.LanguageModel

class LanguageObject(private val context: Context) {

    fun allLanguage(): List<LanguageModel> {
        val items: MutableList<LanguageModel> = mutableListOf()
        items.add(
            LanguageModel(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.united_states_flag_icon
                ), "English (US)", false
            )
        )
        items.add(
            LanguageModel(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.united_kingdom_flag_icon
                ), "English (UK)", false
            )
        )
        items.add(
            LanguageModel(
                ContextCompat.getDrawable(context, R.drawable.indonesia_flag_icon),
                "Indonesia",
                false
            )
        )
        return items
    }
}
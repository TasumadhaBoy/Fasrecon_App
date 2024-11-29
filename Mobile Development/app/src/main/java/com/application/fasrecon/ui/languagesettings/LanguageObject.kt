package com.application.fasrecon.ui.languagesettings

import android.content.Context
import androidx.core.content.ContextCompat
import com.application.fasrecon.R
import com.application.fasrecon.data.model.LanguageModel

class LanguageObject(private val context: Context) {

    fun allLanguage(): MutableList<LanguageModel> {
        val items: MutableList<LanguageModel> = mutableListOf()
        items.add(
            LanguageModel(
                "en-rUS",
                ContextCompat.getDrawable(
                    context,
                    R.drawable.united_states_flag_icon
                ),
                ContextCompat.getString(context, R.string.english_us),
                false
            )
        )
        items.add(
            LanguageModel(
                "en-rGB",
                ContextCompat.getDrawable(
                    context,
                    R.drawable.united_kingdom_flag_icon
                ),
                ContextCompat.getString(context, R.string.english_uk),
                false
            )
        )
        items.add(
            LanguageModel(
                "in",
                ContextCompat.getDrawable(context, R.drawable.indonesia_flag_icon),
                ContextCompat.getString(context, R.string.indonesia),
                false
            )
        )
        return items
    }
}
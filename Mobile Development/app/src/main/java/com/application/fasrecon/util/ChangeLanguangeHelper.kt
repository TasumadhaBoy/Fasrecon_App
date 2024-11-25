package com.application.fasrecon.util

import android.content.Context
import android.content.ContextWrapper
import java.util.Locale

class ChangeLanguangeHelper {
    companion object {
        fun changeLanguange(context: Context, langCode: String): ContextWrapper {
            var newContext = context
            val config = newContext.resources.configuration
            val locale = Locale(langCode)

            config.setLocale(locale)
            newContext = newContext.createConfigurationContext(config)

            return ContextWrapper(newContext)
        }
    }
}
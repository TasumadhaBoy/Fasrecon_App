package com.application.fasrecon.ui

import android.app.Application
import com.application.fasrecon.data.preferences.SettingsPreference
import com.application.fasrecon.data.preferences.settingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale

class FasreconApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val pref = SettingsPreference.getInstance(settingsDataStore)

        CoroutineScope(Dispatchers.IO).launch {
            val langCode = pref.getLanguage().first()
            val locale = Locale(langCode)
            Locale.setDefault(locale)

            resources.configuration.apply {
                setLocale(locale)
                setLayoutDirection(locale)
            }

            createConfigurationContext(resources.configuration)
        }
    }
}
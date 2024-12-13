package com.application.fasrecon.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.application.fasrecon.data.preferences.SettingsPreference
import com.application.fasrecon.data.preferences.settingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

open class BaseActivity: AppCompatActivity() {

    @SuppressLint("AppBundleLocaleChanges")
    override fun attachBaseContext(newBase: Context) {
        val settingsPreference = SettingsPreference.getInstance(settingsDataStore)
        val langCode = runBlocking { settingsPreference.getLanguage().first() }
        val locale = Locale(langCode)
        val config = newBase.resources.configuration

        config.apply {
            setLocale(locale)
            setLayoutDirection(locale)
        }

        super.attachBaseContext(newBase.createConfigurationContext(config))
    }
}
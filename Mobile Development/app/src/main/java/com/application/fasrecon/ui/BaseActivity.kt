package com.application.fasrecon.ui

import android.content.Context
import android.content.res.Configuration
import android.view.ContextThemeWrapper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.application.fasrecon.data.preferences.SettingsPreference
import com.application.fasrecon.data.preferences.settingsDataStore
import com.application.fasrecon.util.ChangeLanguangeHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

open class BaseActivity: AppCompatActivity() {

    private lateinit var pref: SettingsPreference

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
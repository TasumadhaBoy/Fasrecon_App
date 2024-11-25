package com.application.fasrecon.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.fasrecon.data.preferences.SettingsPreference
import com.application.fasrecon.ui.languagesettings.LanguageSettingsViewModel

class SettingsViewModelFactory(private val pref: SettingsPreference): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LanguageSettingsViewModel::class.java)) {
            return LanguageSettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }
}
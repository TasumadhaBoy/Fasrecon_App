package com.application.fasrecon.ui.languagesettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.application.fasrecon.data.preferences.SettingsPreference
import kotlinx.coroutines.launch

class LanguageSettingsViewModel(private val pref: SettingsPreference): ViewModel() {

    fun getLanguageSetting(): LiveData<String> = pref.getLanguage().asLiveData()
    fun saveLanguageSettings(language: String) {
        viewModelScope.launch {
            pref.saveLanguageSettings(language)
        }
    }
}
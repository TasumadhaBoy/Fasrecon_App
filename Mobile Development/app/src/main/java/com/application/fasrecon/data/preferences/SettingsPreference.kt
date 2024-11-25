package com.application.fasrecon.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "languangeSelected")

class SettingsPreference(private val dataStore: DataStore<Preferences>) {

    suspend fun saveLanguageSettings(language: String) {
        dataStore.edit { preferences ->
            preferences[LANG_CODE] = language
        }
    }

    fun getLanguage(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[LANG_CODE] ?: ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreference? = null

        private val LANG_CODE = stringPreferencesKey("langCode")

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
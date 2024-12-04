package com.application.fasrecon.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.application.fasrecon.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "userSession")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession() {
        dataStore.edit { preferences ->
            preferences[IS_LOGIN] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val IS_LOGIN = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
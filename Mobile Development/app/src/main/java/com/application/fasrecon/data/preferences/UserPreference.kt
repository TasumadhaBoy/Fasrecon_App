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

    suspend fun saveSession(user: UserModel, isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGIN] = isLogin
            user.name?.let { preferences[NAME] = it }
            user.email?.let { preferences[EMAIL] = it }
            user.password?.let { preferences[PASSWORD] = it }
            user.photoUrl?.let { preferences[PHOTO] = it }
        }
    }

    fun getData(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME] ?: "",
                preferences[EMAIL] ?: "",
                preferences[PASSWORD] ?: "",
                preferences[PHOTO] ?: ""
            )
        }
    }

    fun getSession(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGIN] ?: false
        }
    }

    suspend fun updateDataLocal(name: String, photo: String){
        dataStore.edit { preferences ->
            preferences[NAME] = name
            preferences[PHOTO] = photo
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
        private val NAME = stringPreferencesKey("userName")
        private val EMAIL = stringPreferencesKey("userEmail")
        private val PASSWORD = stringPreferencesKey("userPassword")
        private val PHOTO = stringPreferencesKey("userPhoto")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
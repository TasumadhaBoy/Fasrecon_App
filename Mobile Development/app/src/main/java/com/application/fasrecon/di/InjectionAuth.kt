package com.application.fasrecon.di

import android.content.Context
import com.application.fasrecon.data.preferences.UserPreference
import com.application.fasrecon.data.preferences.userDataStore
import com.application.fasrecon.data.remote.retrofit.ApiConfig
import com.application.fasrecon.data.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object InjectionAuth {
//    fun createRepository(context: Context): AuthRepository {
//        val pref = UserPreference.getInstance(context.dataStore)
//        val user = runBlocking { pref.getSession().first() }
//        val apiService = ApiConfig.getApiService(user.token)
//
//        return AuthRepository.getInstance(apiService, pref)
//    }
}
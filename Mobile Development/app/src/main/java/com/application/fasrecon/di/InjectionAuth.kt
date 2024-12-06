package com.application.fasrecon.di

import android.content.Context
import com.application.fasrecon.data.preferences.UserPreference
import com.application.fasrecon.data.preferences.userDataStore
import com.application.fasrecon.data.remote.retrofit.ApiConfig
import com.application.fasrecon.data.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object InjectionAuth {
    fun createRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.userDataStore)
        val authUser = FirebaseAuth.getInstance()

        return AuthRepository.getInstance(authUser, pref)
    }
}
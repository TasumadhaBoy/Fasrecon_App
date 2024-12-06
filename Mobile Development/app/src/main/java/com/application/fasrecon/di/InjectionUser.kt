package com.application.fasrecon.di

import android.content.Context
import com.application.fasrecon.data.preferences.UserPreference
import com.application.fasrecon.data.preferences.userDataStore
import com.application.fasrecon.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth

object InjectionUser {
    fun createRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.userDataStore)
        val authUser = FirebaseAuth.getInstance()

        return UserRepository.getInstance(authUser, pref)
    }
}
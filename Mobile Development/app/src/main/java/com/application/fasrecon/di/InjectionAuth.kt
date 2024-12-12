package com.application.fasrecon.di

import android.content.Context
import com.application.fasrecon.data.local.db.FasreconDatabase
import com.application.fasrecon.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

object InjectionAuth {
    fun createRepository(context: Context): AuthRepository {
        val authUser = FirebaseAuth.getInstance()
        val database = FasreconDatabase.getInstance(context)
        val userDao = database.userDao()

        return AuthRepository.getInstance(authUser, userDao)
    }
}
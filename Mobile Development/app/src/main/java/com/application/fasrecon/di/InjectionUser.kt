package com.application.fasrecon.di

import android.content.Context
import com.application.fasrecon.data.local.db.FasreconDatabase
import com.application.fasrecon.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth

object InjectionUser {
    fun createRepository(context: Context): UserRepository {
        val authUser = FirebaseAuth.getInstance()
        val database = FasreconDatabase.getInstance(context)
        val userDao = database.userDao()

        return UserRepository.getInstance(authUser, userDao)
    }
}
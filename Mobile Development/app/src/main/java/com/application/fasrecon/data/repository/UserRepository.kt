package com.application.fasrecon.data.repository

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.application.fasrecon.data.preferences.UserPreference
import com.google.firebase.auth.FirebaseAuth
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.model.UserModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException

class UserRepository(private val user: FirebaseAuth, userPreference: UserPreference) {

    fun getUserData(): LiveData<Result<UserModel>> = liveData {
        emit(Result.Loading)
        try {
            val userData = user.currentUser
            val newUserData = UserModel(userData?.displayName, userData?.email, userData?.photoUrl)
            emit(Result.Success(newUserData))

        } catch (e: Exception) {
            val message = when(e) {
                is FirebaseNetworkException -> "NO_INTERNET"
                is FirebaseAuthException -> "SESSION_EXPIRED"
                else -> "UNKNOWN_ERROR"
            }
        }
    }

    companion object {

        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(user: FirebaseAuth, userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(user, userPreference)
            }.also { instance = it }
    }
}
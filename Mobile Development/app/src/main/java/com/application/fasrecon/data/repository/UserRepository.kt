package com.application.fasrecon.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.application.fasrecon.data.preferences.UserPreference
import com.google.firebase.auth.FirebaseAuth
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.model.UserModel
import com.application.fasrecon.util.WrapMessage
import kotlinx.coroutines.flow.first

class UserRepository(private val user: FirebaseAuth, private val userPreference: UserPreference) {

    fun getUserData(): LiveData<Result<UserModel>> = liveData {
        try {
            val userData = userPreference.getSession().first()
            emit(Result.Success(userData))
        } catch (e: Exception) {
            emit(Result.Error(WrapMessage("Failed to get User Data")))
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
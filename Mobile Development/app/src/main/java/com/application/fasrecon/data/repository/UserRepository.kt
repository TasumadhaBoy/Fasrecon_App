package com.application.fasrecon.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.application.fasrecon.data.preferences.UserPreference
import com.google.firebase.auth.FirebaseAuth
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.model.UserModel
import com.application.fasrecon.util.WrapMessage
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class UserRepository(private val user: FirebaseAuth, private val userPreference: UserPreference) {

    fun getUserData(): LiveData<Result<UserModel>> = liveData {
        try {
            val userData = userPreference.getData().first()
            emit(Result.Success(userData))
        } catch (e: Exception) {
            emit(Result.Error(WrapMessage("Failed to get User Data")))
        }
    }

    fun updateUserData(name: String, photo: String?): LiveData<Result<String?>> =
        liveData {
            emit(Result.Loading)
            try {
                val userData = user.currentUser
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                    photoUri = Uri.parse(photo)
                }
                userData?.updateProfile(profileUpdates)?.await()
                emit(Result.Success("SUCCESS"))
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseNetworkException -> "NO_INTERNET"
                    is FirebaseTooManyRequestsException -> "TOO_MANY_REQUEST"
                    is FirebaseAuthRecentLoginRequiredException -> "LOGIN_AGAIN"
                    else -> "UNKNOWN_ERROR"
                }
                emit(Result.Error(WrapMessage(errorMessage)))
            }
        }

    suspend fun updateUserDataLocal(name: String, photo: String) {
        userPreference.updateDataLocal(name, photo)
    }

    suspend fun logout() {
        userPreference.logout()
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
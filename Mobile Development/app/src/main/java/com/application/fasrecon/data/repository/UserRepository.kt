package com.application.fasrecon.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.local.dao.UserDao
import com.application.fasrecon.data.local.entity.UserEntity
import com.application.fasrecon.util.WrapMessage
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val user: FirebaseAuth,
    private val userDao: UserDao
) {

    fun getUserData(): LiveData<UserEntity> = userDao.getDataUser()

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

    fun changePassword(newPassword: String): LiveData<Result<String?>> = liveData {
        emit(Result.Loading)
        try {
            val userPassword = user.currentUser
            userPassword?.updatePassword(newPassword)
            val id = userDao.getDataUser().value?.id
            userDao.changeUserPassword(newPassword, id)
            emit(Result.Success("SUCCESS"))
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseNetworkException -> "NO_INTERNET"
                is FirebaseTooManyRequestsException -> "TOO_MANY_REQUEST"
                is FirebaseAuthInvalidCredentialsException -> "TOO_SHORT"
                is FirebaseAuthRecentLoginRequiredException -> "LOGIN_AGAIN"
                else -> "UNKNOWN_ERROR"
            }
            emit(Result.Error(WrapMessage(errorMessage)))
        }
    }

    suspend fun updateUserDataLocal(name: String, photo: String?) {
        val id = userDao.getDataUser().value?.id
        userDao.updateProfileUser(name, photo, id)
    }

    suspend fun logout() {
        user.signOut()
        userDao.getDataUser().value?.let { userDao.deleteUser(it.id) }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(user: FirebaseAuth, userDao: UserDao): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(user, userDao)
            }.also { instance = it }
    }
}
package com.application.fasrecon.data.repository

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
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository private constructor(
    private val authUser: FirebaseAuth,
    private val userDao: UserDao
) {

    fun registerAccount(
        username: String,
        email: String,
        password: String
    ): LiveData<Result<String?>> =
        liveData {
            emit(Result.Loading)
            try {
                val authResult = authUser.createUserWithEmailAndPassword(email, password).await()
                val user = authResult.user

                val userProfileUpdate = userProfileChangeRequest {
                    displayName = username
                }
                user?.updateProfile(userProfileUpdate)?.await()
                emit(Result.Success("Success"))
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseNetworkException -> "NO_INTERNET"
                    is FirebaseAuthInvalidCredentialsException -> "WRONG_EMAIL_FORMAT"
                    is FirebaseAuthUserCollisionException -> "EMAIL_REGISTERED"
                    else -> "Error : $e"
                }
                emit(Result.Error(WrapMessage(errorMessage)))
            }
        }

    fun loginAccount(email: String, password: String): LiveData<Result<String?>> = liveData {
        emit(Result.Loading)
        try {
            val authResult = authUser.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user

            emit(Result.Success(user?.displayName.toString()))
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseNetworkException -> "NO_INTERNET"
                is FirebaseAuthInvalidCredentialsException -> "WRONG_EMAIL_PASSWORD"
                is FirebaseAuthInvalidUserException -> "INVALID_USER"
                is FirebaseTooManyRequestsException -> "TOO_MANY_REQUEST"
                else -> "Error : $e"
            }
            emit(Result.Error(WrapMessage(errorMessage)))
        }
    }

    suspend fun saveSession(password: String) {
        val user = authUser.currentUser

        user?.let {
            for (profile in it.providerData) {
                profile.photoUrl
            }
        }

        val newDataUser =
            UserEntity(
                id = user?.uid.toString(),
                name = user?.displayName,
                email = user?.email,
                password = password,
                photoUrl = user?.photoUrl.toString()
            )

        userDao.insertDataUser(newDataUser)
    }

    fun getSession(): Boolean = authUser.currentUser != null

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(
            authUser: FirebaseAuth,
            userDao: UserDao
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(authUser, userDao)
            }.also { instance = it }
    }
}
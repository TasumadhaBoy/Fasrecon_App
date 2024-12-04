package com.application.fasrecon.data.repository

import android.content.Intent
import android.content.res.Resources
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.R
import com.application.fasrecon.data.model.UserModel
import com.application.fasrecon.data.preferences.UserPreference
import com.application.fasrecon.data.remote.retrofit.ApiService
import com.application.fasrecon.data.Result
import com.application.fasrecon.ui.login.LoginActivity
import com.application.fasrecon.util.WrapMessage
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class AuthRepository private constructor(
    private val authUser: FirebaseAuth, private val userPreference: UserPreference
) {

    fun registerAccount(username: String, email: String, password: String): LiveData<Result<String?>> =
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
                    else -> "UNKNOWN ERROR"
                }
                emit(Result.Error(WrapMessage(errorMessage)))
            }
        }

//    fun loginAccount(email: String, password: String): LiveData<Result<LoginResult?>> = liveData {
//        emit(com.application.fasrecon.data.Result.Loading)
//        try {
//            val response = apiService.getLogin(email, password).loginResult
//            emit(Result.Success(response))
//        } catch (e: Exception) {
//            val errorMessage = when (e) {
//                is java.net.UnknownHostException -> "No internet connection"
//                is retrofit2.HttpException -> ""
//                else -> "Unknown Error"
//            }
//            emit(Result.Error(WrapMessage(errorMessage)))
//        }
//    }

    suspend fun saveSession() {
        userPreference.saveSession()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(authUser: FirebaseAuth, preference: UserPreference): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(authUser, preference)
            }.also { instance = it }
    }
}
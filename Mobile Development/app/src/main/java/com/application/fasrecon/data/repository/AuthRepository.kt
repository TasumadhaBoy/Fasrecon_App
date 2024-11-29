package com.application.fasrecon.data.repository

import com.application.fasrecon.data.model.UserModel
import com.application.fasrecon.data.preferences.UserPreference
import com.application.fasrecon.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class AuthRepository private constructor(
    private val apiService: ApiService, private val userPreference: UserPreference
) {

//    fun registerAccount(name: String, email: String, password: String): LiveData<Result<String?>> =
//        liveData {
//            emit(Result.Loading)
//            try {
//                val response = apiService.register(name, email, password).message
//                emit(Result.Success(response))
//            } catch (e: Exception) {
//                val errorMessage = when (e) {
//                    is java.net.UnknownHostException -> "No internet connection"
//                    is retrofit2.HttpException -> ""
//                    else -> "Unknown Error"
//                }
//                emit(Result.Error(WrapMessage(errorMessage)))
//            }
//        }

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

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(apiService: ApiService, preference: UserPreference): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, preference)
            }.also { instance = it }
    }
}
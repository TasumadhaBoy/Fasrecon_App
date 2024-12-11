package com.application.fasrecon.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.application.fasrecon.data.Result
import com.application.fasrecon.data.local.dao.UserDao
import com.application.fasrecon.data.local.entity.ChatEntity
import com.application.fasrecon.data.local.entity.ClothesEntity
import com.application.fasrecon.data.local.entity.MessageEntity
import com.application.fasrecon.data.local.entity.UserEntity
import com.application.fasrecon.data.model.ChatMessage
import com.application.fasrecon.data.remote.response.Label
import com.application.fasrecon.data.remote.retrofit.ApiService
import com.application.fasrecon.util.WrapMessage
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import okhttp3.MultipartBody

class UserRepository(
    private val user: FirebaseAuth,
    private val userDao: UserDao,
    private val apiService: ApiService
) {

    fun getUserData(): LiveData<UserEntity> {
        val id: String = user.currentUser?.uid.toString()
        return userDao.getDataUser(id)
    }

    fun updateUserData(name: String, photo: Uri?): LiveData<Result<String?>> =
        liveData {
            emit(Result.Loading)
            try {
                val userData = user.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder().apply {
                    displayName = name
                    photoUri = photo
                }.build()

                userData?.updateProfile(profileUpdates)?.await()
                emit(Result.Success("SUCCESS"))
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseNetworkException -> "NO_INTERNET"
                    is FirebaseTooManyRequestsException -> "TOO_MANY_REQUEST"
                    is FirebaseAuthRecentLoginRequiredException -> "LOGIN_AGAIN"
                    else -> "Error : $e"
                }
                emit(Result.Error(WrapMessage(errorMessage)))
            }
        }

    fun changePassword(currentPassword: String, newPassword: String): LiveData<Result<String?>> = liveData {
        emit(Result.Loading)
        try {
            val email = user.currentUser?.email
            val currentUser = user.currentUser
            val credential = EmailAuthProvider.getCredential(email ?: "", currentPassword)
            currentUser?.reauthenticate(credential)?.await()
            currentUser?.updatePassword(newPassword)?.await()
            emit(Result.Success("SUCCESS"))
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseNetworkException -> "NO_INTERNET"
                is FirebaseTooManyRequestsException -> "TOO_MANY_REQUEST"
                is FirebaseAuthInvalidCredentialsException -> "INCORRECT_PASSWORD"
                is FirebaseAuthRecentLoginRequiredException -> "LOGIN_AGAIN"
                else -> "Error : $e"
            }
            emit(Result.Error(WrapMessage(errorMessage)))
        }
    }

    suspend fun updateUserDataLocal(name: String, photo: String?) {
        val id: String = user.currentUser?.uid.toString()
        userDao.updateProfileUser(name, photo, id)
    }

    fun classifyImage(multipartBody: MultipartBody.Part): LiveData<Result<Label>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.classifyImage(multipartBody)
            val newLabel = Label(
                response[0][0],
                response[0][1]
            )
            emit(Result.Success(newLabel))
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is java.net.UnknownHostException -> "No internet connection"
                is retrofit2.HttpException -> "Request Failed With Error ${e.code()}"
                else -> "Unknown Error ${e.message}"
            }
            emit(Result.Error(WrapMessage(errorMessage)))
        }
    }

    suspend fun insertClothesData(clothes: String, type: String?, color: String?) {
        val idUser: String = user.currentUser?.uid.toString()
        val size = userDao.getClothesTotal(idUser)
        val newClothesData = ClothesEntity(
            id = idUser + size,
            clothesImage = clothes,
            clothesName = type + size,
            type = type,
            color = color,
            userId = idUser
        )
        userDao.insertClothes(newClothesData)
    }

    suspend fun insertChat(time: String, firstMessage: String) {
        val idUser: String = user.currentUser?.uid.toString()
        val size = userDao.getChatTotal(idUser)
        val newChat = ChatEntity(
            id = idUser + "chathistory" + size,
            chatTitle = "chat$size",
            firstMessage = firstMessage,
            chatTime = time,
            userId = idUser
        )
        userDao.insertChat(newChat)
    }

    suspend fun insertMessage(type: String, msg: String) {
        val idUser: String = user.currentUser?.uid.toString()
        val sizeChat = userDao.getChatTotal(idUser)
        val sizeMessage = userDao.getMessageTotal(idUser)
        val newMessage = MessageEntity(
            id = idUser + "message" + sizeMessage,
            messageType = type,
            message = msg,
            chatId = idUser + "chathistory" + sizeChat
        )
        userDao.insertMessage(newMessage)
    }

    fun getAllClothes(): LiveData<List<ClothesEntity>> {
        val idUser: String = user.currentUser?.uid.toString()
        return userDao.getAllClothes(idUser)
    }

    fun getClothesType(): LiveData<List<String>> {
        val idUser: String = user.currentUser?.uid.toString()
        return userDao.getClothesType(idUser)
    }

    fun getAllClothesByType(type: String): LiveData<List<ClothesEntity>> {
        val idUser: String = user.currentUser?.uid.toString()
        return userDao.getAllClothesByType(idUser, type)
    }

    fun getClothesTotal(): LiveData<Int> {
        val idUser: String = user.currentUser?.uid.toString()
        return userDao.getTotalUserClothes(idUser)
    }

    fun getAllHistoryChat(): LiveData<List<ChatEntity>> {
        val idUser: String = user.currentUser?.uid.toString()
        return userDao.getAllHistoryChat(idUser)
    }

    fun getAllHistoryMessage(): LiveData<List<MessageEntity>> {
        val idUser: String = user.currentUser?.uid.toString()
        val sizeChat = userDao.getChatTotal(idUser)

        return userDao.getAllHistoryMessage(idUser + "chathistory" + sizeChat)
    }

    suspend fun deleteClothes(id: String) {
        userDao.deleteClothes(id)
    }

    suspend fun deleteChat(id: String) {
        val idUser: String = user.currentUser?.uid.toString()
        val sizeChat = userDao.getChatTotal(idUser)
        userDao.deleteHistoryMessage(idUser + "chathistory" + sizeChat)
        userDao.deleteHistoryChat(id)
    }

    suspend fun logout() {
        user.signOut()
        val id: String = user.currentUser?.uid.toString()
        userDao.deleteUser(id)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(user: FirebaseAuth, userDao: UserDao, apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(user, userDao, apiService)
            }.also { instance = it }
    }
}
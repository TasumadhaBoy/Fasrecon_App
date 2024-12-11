package com.application.fasrecon.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.fasrecon.data.local.entity.ChatEntity
import com.application.fasrecon.data.local.entity.ClothesEntity
import com.application.fasrecon.data.local.entity.MessageEntity
import com.application.fasrecon.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClothes(clothes: ClothesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("UPDATE user SET name = :name, photoUrl = :photo WHERE id = :id")
    suspend fun updateProfileUser(name: String, photo: String?, id: String)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getDataUser(id: String): LiveData<UserEntity>

    @Query("SELECT * FROM clothes where userId = :id")
    fun getAllClothes(id: String): LiveData<List<ClothesEntity>>

    @Query("SELECT COUNT(*) FROM clothes WHERE userId = :id")
    fun getClothesTotal(id: String): Int

    @Query("SELECT COUNT(*) FROM clothes WHERE userId = :id")
    fun getTotalUserClothes(id: String): LiveData<Int>

    @Query("SELECT DISTINCT type FROM clothes WHERE userId = :id")
    fun getClothesType(id: String): LiveData<List<String>>

    @Query("SELECT * FROM clothes WHERE userId = :id and type = :type")
    fun getAllClothesByType(id: String, type: String): LiveData<List<ClothesEntity>>

    @Query("SELECT * FROM chatEntity WHERE userId = :id")
    fun getAllHistoryChat(id: String): LiveData<List<ChatEntity>>

    @Query("SELECT * FROM messageEntity WHERE chatId = :id")
    fun getAllHistoryMessage(id: String): LiveData<List<MessageEntity>>

    @Query("SELECT COUNT(*) FROM chatEntity WHERE userId = :id")
    fun getChatTotal(id: String): Int

    @Query("SELECT COUNT(*) FROM messageEntity WHERE chatId = :id")
    fun getMessageTotal(id: String): Int

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: String)

    @Query("DELETE FROM clothes WHERE id = :id")
    suspend fun deleteClothes(id: String)

    @Query("DELETE FROM chatEntity WHERE id = :id")
    suspend fun deleteHistoryChat(id: String)

    @Query("DELETE FROM messageEntity WHERE chatId = :id")
    suspend fun deleteHistoryMessage(id: String)
}
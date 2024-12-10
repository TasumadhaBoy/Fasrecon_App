package com.application.fasrecon.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.application.fasrecon.data.local.entity.ClothesEntity
import com.application.fasrecon.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClothes(clothes: ClothesEntity)

    @Query("UPDATE user SET name = :name, photoUrl = :photo WHERE id = :id")
    suspend fun updateProfileUser(name: String, photo: String?, id: String)

    @Query("SELECT * FROM user")
    fun getDataUser(): LiveData<UserEntity>

    @Query("SELECT COUNT(*) FROM clothes WHERE userId = :id")
    suspend fun getClothesCount(id: String): Int

    @Transaction
    @Query("SELECT * FROM clothes where userId = :id")
    fun getAllClothes(id: String): LiveData<List<ClothesEntity>>

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: String)

    @Query("DELETE FROM clothes WHERE id = :id")
    suspend fun deleteClothes(id: String)
}
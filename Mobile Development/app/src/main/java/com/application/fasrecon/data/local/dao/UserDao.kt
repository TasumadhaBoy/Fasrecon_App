package com.application.fasrecon.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.application.fasrecon.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataUser(user: UserEntity)

    @Query("UPDATE user SET name = :name, photoUrl = :photo WHERE id = :id")
    suspend fun updateProfileUser(name: String, photo: String?, id: String)

    @Query("SELECT * FROM user")
    fun getDataUser(): LiveData<UserEntity>

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: String)
}
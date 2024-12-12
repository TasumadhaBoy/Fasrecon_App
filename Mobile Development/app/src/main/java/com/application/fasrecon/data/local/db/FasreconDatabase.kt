package com.application.fasrecon.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.application.fasrecon.data.local.dao.UserDao
import com.application.fasrecon.data.local.entity.ChatEntity
import com.application.fasrecon.data.local.entity.ClothesEntity
import com.application.fasrecon.data.local.entity.MessageEntity
import com.application.fasrecon.data.local.entity.PhotosConverters
import com.application.fasrecon.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, ClothesEntity::class, ChatEntity::class, MessageEntity::class], version = 1, exportSchema = false)
@TypeConverters(PhotosConverters::class)
abstract class FasreconDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile

        private var instance: FasreconDatabase? = null

        fun getInstance(context: Context): FasreconDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext, FasreconDatabase::class.java, "fasrecon.db").build()
            }
    }
}
package com.application.fasrecon.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.application.fasrecon.data.local.dao.UserDao
import com.application.fasrecon.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)

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
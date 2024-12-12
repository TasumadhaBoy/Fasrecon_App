package com.application.fasrecon.data.local.entity

import androidx.room.TypeConverter

class PhotosConverters {
    @TypeConverter
    fun listToString(value: List<String>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToList(value: String): List<String> {
        return value.split(",")
    }
}
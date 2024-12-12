package com.application.fasrecon.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter

@Entity(tableName = "user")
data class UserEntity (

    @ColumnInfo("id")
    @PrimaryKey
    val id: String,

    @ColumnInfo("name")
    val name: String?,

    @ColumnInfo("email")
    val email: String?,

    @ColumnInfo("password")
    val password: String?,

    @ColumnInfo("photoUrl")
    val photoUrl: String?
)

@Entity(tableName = "clothes")
data class ClothesEntity(
    @ColumnInfo("id")
    @PrimaryKey
    val id: String,

    @ColumnInfo("clothesImage")
    val clothesImage: String,

    @ColumnInfo("clothesName")
    val clothesName: String?,

    @ColumnInfo("type")
    val type: String?,

    @ColumnInfo("color")
    val color: String?,

    @ColumnInfo("userId")
    val userId: String
)

@Entity(tableName = "ChatEntity")
data class ChatEntity(
    @ColumnInfo("id")
    @PrimaryKey
    val id: String,

    @ColumnInfo("ChatTitle")
    val chatTitle: String?,

    @ColumnInfo("firstMessage")
    val firstMessage: String,

    @ColumnInfo("chatTime")
    val chatTime: String,

    @ColumnInfo("userId")
    val userId: String
)

@Entity(tableName = "messageEntity")
data class MessageEntity(
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("messageType")
    val messageType: String,

    @ColumnInfo("firstMessage")
    val message: String,

    @ColumnInfo("photoProfile")
    val photoProfile: String,

    @ColumnInfo("listPhoto")
    val listPhoto: List<String>?,

    @ColumnInfo("chatId")
    val chatId: String
)

//data class HistoryMessage(
//    @Embedded
//    val chat: ChatEntity,
//
//
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "chatId"
//    )
//
//    val message: MessageEntity
//
//)
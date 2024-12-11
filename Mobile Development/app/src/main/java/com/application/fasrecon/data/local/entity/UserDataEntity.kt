package com.application.fasrecon.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

//data class ClothesUser(
//    @Embedded
//    val clothes: ClothesEntity,
//
//    @Relation(
//        parentColumn = "userId",
//        entityColumn = "id"
//    )
//
//    val user: UserEntity
//)
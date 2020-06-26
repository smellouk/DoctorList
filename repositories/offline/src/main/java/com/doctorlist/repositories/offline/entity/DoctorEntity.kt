package com.doctorlist.repositories.offline.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_doctors")
data class DoctorEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "photoId")
    val photoId: String? = null,

    @ColumnInfo(name = "rating")
    val rating: Double? = null,

    @ColumnInfo(name = "address")
    val address: String? = null,

    @ColumnInfo(name = "reviewCount")
    val reviewCount: Int? = null,

    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String? = null,

    @ColumnInfo(name = "email")
    val email: String? = null,

    @ColumnInfo(name = "website")
    val website: String? = null,

    @ColumnInfo(name = "date")
    val date: Long = System.currentTimeMillis()
)
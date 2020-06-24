package com.doctorlist.common.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Doctor(
    val id: String,
    val name: String,
    val photoId: String,
    val rating: Double,
    val address: String,
    val reviewCount: Int,
    val phoneNumber: String,
    val email: String,
    val website: String,
    val openingHours: List<String>
) : Parcelable
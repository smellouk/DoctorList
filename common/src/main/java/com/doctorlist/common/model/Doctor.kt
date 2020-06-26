package com.doctorlist.common.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Doctor(
    val id: String,
    val name: String,
    val photoId: String,
    val rating: String,
    val address: String,
    val reviewCount: String,
    val phoneNumber: String,
    val email: String,
    val website: String
) : Parcelable
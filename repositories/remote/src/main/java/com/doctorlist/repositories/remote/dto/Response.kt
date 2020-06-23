package com.doctorlist.repositories.remote.dto

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("doctors")
    val doctors: List<Doctor?>? = null,

    @SerializedName("lastKey")
    val lastKey: String? = null
)

class Doctor(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("photoId")
    val photoId: String? = null,

    @SerializedName("rating")
    val rating: Double? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("reviewCount")
    val reviewCount: Int? = null,

    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @SerializedName("email")
    val email: Any? = null,

    @SerializedName("website")
    val website: String? = null,

    @SerializedName("openingHours")
    val openingHours: List<String>? = null
)

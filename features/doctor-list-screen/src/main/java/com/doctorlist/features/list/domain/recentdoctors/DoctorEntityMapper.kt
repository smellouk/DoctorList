package com.doctorlist.features.list.domain.recentdoctors

import com.doctorlist.common.model.Doctor
import com.doctorlist.repositories.offline.entity.DoctorEntity

class DoctorEntityMapper {
    fun map(doctor: Doctor) = with(doctor) {
        DoctorEntity(
            id = id,
            name = name,
            photoId = photoId,
            rating = rating,
            address = address,
            reviewCount = reviewCount,
            phoneNumber = phoneNumber,
            email = email,
            website = website
        )
    }
}
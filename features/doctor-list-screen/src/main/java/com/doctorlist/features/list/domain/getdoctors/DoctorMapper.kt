package com.doctorlist.features.list.domain.getdoctors

import com.doctorlist.common.model.Doctor
import com.doctorlist.common.utils.Defaults
import com.doctorlist.repositories.offline.entity.DoctorEntity
import com.doctorlist.repositories.remote.dto.DoctorDto
import com.doctorlist.repositories.remote.dto.Response

class DoctorMapper {
    fun map(response: Response) = response.doctors?.let { dtoList ->
        mapDtoList(dtoList)
    } ?: emptyList()

    fun mapDtoList(dtoList: List<DoctorDto>) = dtoList.map { dto ->
        map(dto)
    }

    fun mapEntityList(entities: List<DoctorEntity>) =
        entities.map { entity ->
            map(entity)
        }

    fun map(dto: DoctorDto) = with(dto) {
        Doctor(
            id = id ?: Defaults.DEFAULT_STRING,
            name = name ?: Defaults.DEFAULT_STRING,
            photoId = photoId ?: Defaults.DEFAULT_STRING,
            rating = rating ?: Defaults.DEFAULT_DOUBLE,
            address = address ?: Defaults.DEFAULT_STRING,
            reviewCount = reviewCount ?: Defaults.DEFAULT_INT,
            phoneNumber = phoneNumber ?: Defaults.DEFAULT_STRING,
            email = email ?: Defaults.DEFAULT_STRING,
            website = website ?: Defaults.DEFAULT_STRING
        )
    }

    fun map(entity: DoctorEntity) = with(entity) {
        Doctor(
            id = id,
            name = name ?: Defaults.DEFAULT_STRING,
            photoId = photoId ?: Defaults.DEFAULT_STRING,
            rating = rating ?: Defaults.DEFAULT_DOUBLE,
            address = address ?: Defaults.DEFAULT_STRING,
            reviewCount = reviewCount ?: Defaults.DEFAULT_INT,
            phoneNumber = phoneNumber ?: Defaults.DEFAULT_STRING,
            email = email ?: Defaults.DEFAULT_STRING,
            website = website ?: Defaults.DEFAULT_STRING
        )
    }
}
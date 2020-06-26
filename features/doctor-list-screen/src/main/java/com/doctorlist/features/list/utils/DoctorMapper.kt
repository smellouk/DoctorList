package com.doctorlist.features.list.utils

import com.doctorlist.common.model.Doctor
import com.doctorlist.common.utils.Defaults
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.repositories.offline.entity.DoctorEntity
import com.doctorlist.repositories.remote.dto.DoctorDto
import com.doctorlist.repositories.remote.dto.Response
import java.text.DecimalFormat
import javax.inject.Inject

@DoctorListScope
class DoctorMapper @Inject constructor() {
    private val rateFormatter = DecimalFormat(RATE_FORMAT)

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
            rating = (rating ?: Defaults.DEFAULT_DOUBLE).toFormattedRate(),
            address = address ?: Defaults.DEFAULT_STRING,
            reviewCount = reviewCount?.toString() ?: Defaults.DEFAULT_STRING,
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
            rating = rating ?: Defaults.DEFAULT_STRING,
            address = address ?: Defaults.DEFAULT_STRING,
            reviewCount = reviewCount?.toString() ?: Defaults.DEFAULT_STRING,
            phoneNumber = phoneNumber ?: Defaults.DEFAULT_STRING,
            email = email ?: Defaults.DEFAULT_STRING,
            website = website ?: Defaults.DEFAULT_STRING
        )
    }

    private fun Double.toFormattedRate() = rateFormatter.format(this)
}

private const val RATE_FORMAT = "##.##"
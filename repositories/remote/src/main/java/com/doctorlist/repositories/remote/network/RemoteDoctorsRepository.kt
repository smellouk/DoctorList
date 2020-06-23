package com.doctorlist.repositories.remote.network

import com.doctorlist.repositories.remote.dto.Response
import io.reactivex.Single

class RemoteDoctorsRepository(private val doctorsService: DoctorsService) {

    fun getDoctors(lastKey: String? = null): Single<Response> = lastKey?.let {
        doctorsService.getDoctors(it)
    } ?: doctorsService.getDoctors()
}
package com.doctorlist.repositories.remote.network

import com.doctorlist.repositories.remote.dto.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DoctorsService {
    @GET("interviews/challenges/android/doctors.json")
    fun getDoctors(): Single<Response>

    @GET("interviews/challenges/android/doctors-{lastKey}.json")
    fun getDoctors(
        @Path("lastKey")
        lastKey: String
    ): Single<Response>
}
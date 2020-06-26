package com.doctorlist.features.list.domain.getdoctors

import com.doctorlist.common.domain.BaseUseCase
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import io.reactivex.Observable
import javax.inject.Inject

@DoctorListScope
class GetDoctorsUseCase @Inject constructor(
    private val remoteDoctorsRepository: RemoteDoctorsRepository,
    private val doctorMapper: DoctorMapper
) : BaseUseCase<DoctorDataState> {
    override fun buildObservable(): Observable<DoctorDataState> =
        remoteDoctorsRepository.getDoctors()
            .toObservable()
            .map { response ->
                val doctors = doctorMapper.map(response)
                if (doctors.isNotEmpty()) {
                    DoctorDataState.Success(doctors, response.lastKey)
                } else {
                    DoctorDataState.Empty
                }
            }
}
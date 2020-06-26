package com.doctorlist.features.list.domain.getdoctors

import com.doctorlist.common.domain.BaseParams
import com.doctorlist.common.domain.BaseUseCaseWithParams
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import io.reactivex.Observable

class LoadNextDoctorListPageUseCase(
    private val remoteDoctorsRepository: RemoteDoctorsRepository,
    private val doctorMapper: DoctorMapper
) : BaseUseCaseWithParams<DoctorDataState, LoadNextDoctorListPageParams> {
    override fun buildObservable(params: LoadNextDoctorListPageParams): Observable<DoctorDataState> =
        remoteDoctorsRepository.getDoctors(params.lastKey)
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

class LoadNextDoctorListPageParams(val lastKey: String) : BaseParams
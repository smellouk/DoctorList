package com.doctorlist.features.list.domain.remotedoctors

import com.doctorlist.common.domain.BaseUseCaseWithParams
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.domain.LoadNextDoctorListPageParams
import com.doctorlist.features.list.utils.DoctorMapper
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import io.reactivex.Observable
import javax.inject.Inject

@DoctorListScope
class LoadNextRemoteDoctorListPageUseCase @Inject constructor(
    private val remoteDoctorsRepository: RemoteDoctorsRepository,
    private val doctorMapper: DoctorMapper
) : BaseUseCaseWithParams<RemoteDoctorDataState, LoadNextDoctorListPageParams> {
    override fun buildObservable(params: LoadNextDoctorListPageParams): Observable<RemoteDoctorDataState> =
        remoteDoctorsRepository.getDoctors(params.lastKey)
            .toObservable()
            .map { response ->
                val doctors = doctorMapper.map(response)
                if (doctors.isNotEmpty()) {
                    RemoteDoctorDataState.Success(doctors, response.lastKey)
                } else {
                    RemoteDoctorDataState.Empty
                }
            }
}
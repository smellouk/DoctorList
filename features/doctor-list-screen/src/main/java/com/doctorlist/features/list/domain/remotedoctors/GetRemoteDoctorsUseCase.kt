package com.doctorlist.features.list.domain.remotedoctors

import com.doctorlist.common.domain.BaseDataState
import com.doctorlist.common.domain.BaseUseCase
import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.utils.DoctorMapper
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import io.reactivex.Observable
import javax.inject.Inject

@DoctorListScope
class GetRemoteDoctorsUseCase @Inject constructor(
    private val remoteDoctorsRepository: RemoteDoctorsRepository,
    private val doctorMapper: DoctorMapper
) : BaseUseCase<RemoteDoctorDataState> {
    override fun buildObservable(): Observable<RemoteDoctorDataState> =
        remoteDoctorsRepository.getDoctors()
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

sealed class RemoteDoctorDataState : BaseDataState {
    class Success(val list: List<Doctor>, val nextPage: String? = null) : RemoteDoctorDataState()
    object Empty : RemoteDoctorDataState()
}
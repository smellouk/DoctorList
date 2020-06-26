package com.doctorlist.features.list.domain.recentdoctors

import com.doctorlist.common.domain.BaseCompletableUseCase
import com.doctorlist.repositories.offline.db.RecentVisitedDoctorsRepository
import io.reactivex.Completable

class AddDoctorToRecentUseCase(
    private val recentVisitedDoctorsRepository: RecentVisitedDoctorsRepository,
    private val doctorEntityMapper: DoctorEntityMapper
) : BaseCompletableUseCase<AddDoctorToRecentParams> {
    override fun buildObservable(params: AddDoctorToRecentParams): Completable =
        recentVisitedDoctorsRepository.insert(
            doctorEntityMapper.map(params.doctor)
        )
}
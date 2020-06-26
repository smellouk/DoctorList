package com.doctorlist.features.list.domain.recentdoctors

import com.doctorlist.common.domain.BaseUseCase
import com.doctorlist.features.list.domain.getdoctors.DoctorMapper
import com.doctorlist.repositories.offline.db.RecentVisitedDoctorsRepository
import io.reactivex.Maybe
import io.reactivex.Observable

class GetRecentDoctorUseCase(
    private val recentVisitedDoctorsRepository: RecentVisitedDoctorsRepository,
    private val doctorMapper: DoctorMapper
) : BaseUseCase<RecentDoctorDataState> {
    override fun buildObservable(): Observable<RecentDoctorDataState> =
        recentVisitedDoctorsRepository.getRecent()
            .switchIfEmpty(Maybe.just(emptyList()))
            .toObservable()
            .map { entities ->
                if (entities.isEmpty()) {
                    RecentDoctorDataState.Empty
                } else {
                    RecentDoctorDataState.Success(
                        doctorMapper.mapEntityList(entities)
                    )
                }
            }

}
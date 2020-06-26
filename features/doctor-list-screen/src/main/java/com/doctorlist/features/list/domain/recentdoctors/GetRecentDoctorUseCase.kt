package com.doctorlist.features.list.domain.recentdoctors

import com.doctorlist.common.domain.BaseUseCase
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.utils.Constants.MAX_RECENT_ITEMS
import com.doctorlist.features.list.utils.DoctorMapper
import com.doctorlist.repositories.offline.db.RecentVisitedDoctorsRepository
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject

@DoctorListScope
class GetRecentDoctorUseCase @Inject constructor(
    private val recentVisitedDoctorsRepository: RecentVisitedDoctorsRepository,
    private val doctorMapper: DoctorMapper
) : BaseUseCase<RecentDoctorDataState> {
    override fun buildObservable(): Observable<RecentDoctorDataState> =
        recentVisitedDoctorsRepository.getRecent(MAX_RECENT_ITEMS)
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
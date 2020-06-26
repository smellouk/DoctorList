package com.doctorlist.features.list.domain

import com.doctorlist.common.domain.BaseUseCase
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.domain.recentdoctors.GetRecentDoctorUseCase
import com.doctorlist.features.list.domain.recentdoctors.RecentDoctorDataState
import com.doctorlist.features.list.domain.remotedoctors.GetRemoteDoctorsUseCase
import com.doctorlist.features.list.domain.remotedoctors.RemoteDoctorDataState
import com.doctorlist.features.list.utils.ListItemCreator
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

@DoctorListScope
class GetDoctorsUseCase @Inject constructor(
    private val getRemoteDoctorsUseCase: GetRemoteDoctorsUseCase,
    private val getRecentDoctorUseCase: GetRecentDoctorUseCase,
    private val listItemCreator: ListItemCreator
) : BaseUseCase<DoctorListDataState> {
    override fun buildObservable(): Observable<DoctorListDataState> = getRemoteDoctorsUseCase
        .buildObservable()
        .zipWithRecentDoctors()

    private fun Observable<RemoteDoctorDataState>.zipWithRecentDoctors() = zipWith(
        getRecentDoctorUseCase.buildObservable(),
        BiFunction<RemoteDoctorDataState, RecentDoctorDataState, DoctorListDataState> { vivyDoctorData, recentDoctorsData ->
            when {
                recentDoctorsData is RecentDoctorDataState.Success &&
                        vivyDoctorData is RemoteDoctorDataState.Success -> {
                    DoctorListDataState.Success(
                        listItemCreator.createFullList(recentDoctorsData, vivyDoctorData),
                        vivyDoctorData.nextPage
                    )
                }
                recentDoctorsData is RecentDoctorDataState.Success &&
                        vivyDoctorData is RemoteDoctorDataState.Empty -> {
                    DoctorListDataState.Success(
                        listItemCreator.createRecentList(recentDoctorsData),
                        null
                    )
                }
                recentDoctorsData is RecentDoctorDataState.Empty &&
                        vivyDoctorData is RemoteDoctorDataState.Success -> {
                    DoctorListDataState.Success(
                        listItemCreator.createVivyList(vivyDoctorData),
                        vivyDoctorData.nextPage
                    )
                }
                else -> {
                    DoctorListDataState.Fail
                }
            }
        }
    )
}
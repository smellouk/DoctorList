package com.doctorlist.features.list.domain

import com.doctorlist.common.domain.BaseUseCaseWithParams
import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.domain.recentdoctors.GetRecentDoctorUseCase
import com.doctorlist.features.list.domain.recentdoctors.RecentDoctorDataState
import com.doctorlist.features.list.domain.remotedoctors.LoadNextRemoteDoctorListPageUseCase
import com.doctorlist.features.list.domain.remotedoctors.RemoteDoctorDataState
import com.doctorlist.features.list.model.Item
import com.doctorlist.features.list.utils.ListItemCreator
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

@DoctorListScope
class LoadNextDoctorListPageUseCase @Inject constructor(
    private val loadNextRemoteDoctorListPageUseCase: LoadNextRemoteDoctorListPageUseCase,
    private val getRecentDoctorUseCase: GetRecentDoctorUseCase,
    private val listItemCreator: ListItemCreator
) : BaseUseCaseWithParams<DoctorListDataState, LoadNextDoctorListPageParams> {

    override fun buildObservable(
        params: LoadNextDoctorListPageParams
    ): Observable<DoctorListDataState> =
        loadNextRemoteDoctorListPageUseCase.buildObservable(params)
            .zipWithRecentDoctors(params)

    private fun Observable<RemoteDoctorDataState>.zipWithRecentDoctors(
        params: LoadNextDoctorListPageParams
    ) = zipWith(
        getRecentDoctorUseCase.buildObservable(),
        BiFunction<RemoteDoctorDataState, RecentDoctorDataState, DoctorListDataState> { doctorData, recentDoctorsData ->
            when {
                recentDoctorsData is RecentDoctorDataState.Success &&
                        doctorData is RemoteDoctorDataState.Success -> {
                    val result: List<Doctor> = doctorData.list - recentDoctorsData.list
                    createDataState(params.currentList, result, doctorData.nextPage)
                }
                recentDoctorsData is RecentDoctorDataState.Empty &&
                        doctorData is RemoteDoctorDataState.Success -> {
                    createDataState(params.currentList, doctorData.list, doctorData.nextPage)
                }
                else -> {
                    DoctorListDataState.Fail
                }
            }
        }
    )

    private fun createDataState(
        currentList: List<Item>,
        list: List<Doctor>,
        nextPage: String?
    ): DoctorListDataState {
        val headerList = currentList.filter { item ->
            item !is Item.DoctorItem.VivyDoctorItem && item !is Item.VivyDoctorView
        }

        val oldVivyList = currentList.filterIsInstance<Item.DoctorItem.VivyDoctorItem>()
            .map { item ->
                item.doctor
            }

        val vivyList = listItemCreator.createVivyList(oldVivyList + list)

        return DoctorListDataState.Success(
            headerList + vivyList,
            nextPage
        )
    }
}
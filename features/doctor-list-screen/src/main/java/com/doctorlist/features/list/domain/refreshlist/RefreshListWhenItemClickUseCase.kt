package com.doctorlist.features.list.domain.refreshlist

import com.doctorlist.common.domain.BaseDataState
import com.doctorlist.common.domain.BaseParams
import com.doctorlist.common.domain.BaseUseCaseWithParams
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.domain.recentdoctors.GetRecentDoctorUseCase
import com.doctorlist.features.list.domain.recentdoctors.RecentDoctorDataState
import com.doctorlist.features.list.model.Item
import com.doctorlist.features.list.utils.Constants.MAX_RECENT_ITEMS
import com.doctorlist.features.list.utils.ListItemCreator
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@DoctorListScope
class RefreshListWhenItemClickUseCase @Inject constructor(
    private val listItemCreator: ListItemCreator,
    private val getRecentDoctorUseCase: GetRecentDoctorUseCase
) :
    BaseUseCaseWithParams<RefreshListDataState, RefreshListParams> {
    override fun buildObservable(
        params: RefreshListParams
    ): Observable<RefreshListDataState> = Single.create<RefreshListDataState> { emitter ->
        val vivyDoctors = params.items.filterIsInstance<Item.DoctorItem.VivyDoctorItem>()
            .map { item ->
                item.doctor
            }.toMutableList()

        val oldRecent = params.items.filterIsInstance<Item.DoctorItem.RecentDoctorItem>()
            .map { item ->
                item.doctor
            }

        val dataState = getRecentDoctorUseCase.buildObservable().blockingFirst()

        val newRecentDoctors = if (dataState is RecentDoctorDataState.Success) {
            dataState.list
        } else {
            oldRecent
        }

        //Too not loose the extra difference
        if (oldRecent.size == MAX_RECENT_ITEMS) {
            vivyDoctors.add(oldRecent.last())
        }

        emitter.onSuccess(
            RefreshListDataState(
                listItemCreator.createFullList(newRecentDoctors, vivyDoctors)
            )
        )
    }.toObservable()
}

class RefreshListParams(val items: List<Item>) : BaseParams
class RefreshListDataState(val list: List<Item>) : BaseDataState
package com.doctorlist.features.list

import com.doctorlist.common.domain.BaseDataState
import com.doctorlist.features.list.ViewState.*
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.domain.DoctorListDataState
import com.doctorlist.features.list.domain.refreshlist.RefreshListDataState
import javax.inject.Inject

@DoctorListScope
class ViewStateMapper @Inject constructor() {

    fun map(state: BaseDataState) = when (state) {
        is DoctorListDataState.Success -> createDoctorListState(state)
        is DoctorListDataState.Fail -> createErrorState()
        is RefreshListDataState -> RefreshListReady(state.list)
        else -> throw IllegalArgumentException("Data state is not handled here.!")
    }

    fun map(throwable: Throwable?): Error =
        Error(message = throwable?.message)

    private fun createDoctorListState(success: DoctorListDataState.Success): DoctorListReady =
        DoctorListReady(
            list = success.list,
            nextPage = success.nextPage
        )

    private fun createErrorState(message: String? = null): Error =
        Error(message)
}
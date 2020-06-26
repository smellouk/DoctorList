package com.doctorlist.features.list

import com.doctorlist.common.exhaustive
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.domain.pageddoctorlist.PagedDoctorDataState
import com.doctorlist.features.list.domain.pageddoctorlist.PagedDoctorDataState.Fail
import com.doctorlist.features.list.domain.pageddoctorlist.PagedDoctorDataState.Success
import com.doctorlist.features.list.domain.pageddoctorlist.RequestState
import javax.inject.Inject

@DoctorListScope
class ViewStateMapper @Inject constructor() {
    fun map(state: PagedDoctorDataState) = when (state) {
        is Success -> createDoctorListState(state)
        is Fail -> createErrorState(state.message)
    }.exhaustive

    fun map(state: RequestState) = when (state) {
        is RequestState.Loading -> ViewState.Loading
        is RequestState.StopLoading -> ViewState.StopLoading
        is RequestState.Fail -> createErrorState(state.message)
    }.exhaustive

    fun map(throwable: Throwable?): ViewState.Error =
        ViewState.Error(message = throwable?.message)

    private fun createDoctorListState(success: Success): ViewState.DoctorListReady =
        ViewState.DoctorListReady(
            list = success.list
        )

    private fun createErrorState(message: String?): ViewState.Error =
        ViewState.Error(message)
}
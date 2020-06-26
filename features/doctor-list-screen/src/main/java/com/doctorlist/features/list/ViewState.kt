package com.doctorlist.features.list

import androidx.paging.PagedList
import com.doctorlist.common.base.BaseViewState
import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.model.ListItem

sealed class ViewState : BaseViewState {
    object Initial : ViewState()
    object Pending : ViewState()
    object Loading : ViewState()
    object StopLoading : ViewState()
    class DoctorDetails(val doctor: Doctor) : ViewState()
    class Error(val message: String? = null) : ViewState()
    class DoctorListReady(
        val list: PagedList<ListItem>
    ) : ViewState()
}
package com.doctorlist.features.list

import com.doctorlist.common.base.BaseViewState
import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.model.Item

sealed class ViewState : BaseViewState {
    object Initial : ViewState()
    object Pending : ViewState()
    object StopLoading : ViewState()
    class DoctorDetails(val doctor: Doctor) : ViewState()
    class Error(val message: String? = null) : ViewState()
    class DoctorListReady(val list: List<Item>, val nextPage: String?) : ViewState()
    class RefreshListReady(val list: List<Item>) : ViewState()
}
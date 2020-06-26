package com.doctorlist.features.list.domain.pageddoctorlist

sealed class RequestState {
    object Loading : RequestState()
    object StopLoading : RequestState()
    class Fail(val message: String? = null) : RequestState()
}
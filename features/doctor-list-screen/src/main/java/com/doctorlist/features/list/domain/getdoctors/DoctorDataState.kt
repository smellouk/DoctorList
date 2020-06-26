package com.doctorlist.features.list.domain.getdoctors

import com.doctorlist.common.domain.BaseDataState
import com.doctorlist.common.model.Doctor

sealed class DoctorDataState : BaseDataState {
    class Success(val list: List<Doctor>, val nextPage: String? = null) : DoctorDataState()
    object Empty : DoctorDataState()
}
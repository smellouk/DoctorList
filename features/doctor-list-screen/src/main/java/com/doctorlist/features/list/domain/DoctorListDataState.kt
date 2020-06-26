package com.doctorlist.features.list.domain

import com.doctorlist.common.domain.BaseDataState
import com.doctorlist.features.list.model.Item

sealed class DoctorListDataState : BaseDataState {
    class Success(val list: List<Item>, val nextPage: String? = null) : DoctorListDataState()
    object Fail : DoctorListDataState()
}
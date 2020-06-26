package com.doctorlist.features.list.domain.recentdoctors

import com.doctorlist.common.domain.BaseDataState
import com.doctorlist.common.model.Doctor

sealed class RecentDoctorDataState : BaseDataState {
    class Success(val list: List<Doctor>) : RecentDoctorDataState()
    object Empty : RecentDoctorDataState()
}
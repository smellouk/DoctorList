package com.doctorlist.features.main

import androidx.annotation.IdRes
import com.doctorlist.common.base.BaseViewState
import com.doctorlist.common.model.Doctor

sealed class ViewState : BaseViewState {
    object Initial : ViewState()
    object Pending : ViewState()
    object DoctorList : ViewState() {
        @IdRes
        val destination: Int = R.id.doctor_list_fragment
    }

    class DoctorDetails(val doctor: Doctor) : ViewState() {
        @IdRes
        val destination: Int = R.id.doctor_details_fragment
    }
}
package com.doctorlist.features.main

import androidx.annotation.IdRes
import com.doctorlist.common.base.BaseViewState
import com.doctorlist.common.model.Doctor

sealed class ViewState : BaseViewState {
    object Initial : ViewState()
    object DoctorList : ViewState() {
        @IdRes
        val destination: Int = R.id.show_doctor_list
    }

    class DoctorDetails(val doctor: Doctor) : ViewState() {
        @IdRes
        val destination: Int = R.id.show_doctor_list
    }
}
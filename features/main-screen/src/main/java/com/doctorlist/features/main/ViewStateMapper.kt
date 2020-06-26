package com.doctorlist.features.main

import com.doctorlist.common.exhaustive
import com.doctorlist.features.main.Command.*
import com.doctorlist.features.main.ViewState.DoctorDetails
import com.doctorlist.features.main.ViewState.DoctorList
import com.doctorlist.features.main.di.MainScope
import javax.inject.Inject

@MainScope
class ViewStateMapper @Inject constructor() {
    fun map(cmd: Command) = when (cmd) {
        is OpenDoctorList -> DoctorList
        is OpenDoctorDetails -> DoctorDetails(cmd.doctor)
        is SetViewState -> cmd.state
    }.exhaustive
}
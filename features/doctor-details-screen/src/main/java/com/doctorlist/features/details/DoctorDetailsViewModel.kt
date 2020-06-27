package com.doctorlist.features.details

import com.doctorlist.common.base.BaseViewModel
import com.doctorlist.features.details.di.DoctorDetailsScope
import javax.inject.Inject

@DoctorDetailsScope
class DoctorDetailsViewModel @Inject constructor() : BaseViewModel<ViewState>() {
    override fun getInitialState(): ViewState = ViewState.Initial
}
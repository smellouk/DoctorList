package com.doctorlist.features.details

import com.doctorlist.common.base.BaseViewState

sealed class ViewState : BaseViewState {
    object Initial : ViewState()
}
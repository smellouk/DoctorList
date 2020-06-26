package com.doctorlist.features.list.domain.pageddoctorlist

import androidx.paging.PagedList
import com.doctorlist.common.domain.BaseDataState
import com.doctorlist.features.list.model.ListItem

sealed class PagedDoctorDataState : BaseDataState {
    class Success(val list: PagedList<ListItem>) : PagedDoctorDataState()
    class Fail(val message: String?) : PagedDoctorDataState()
}
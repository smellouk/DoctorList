package com.doctorlist.features.list.domain

import com.doctorlist.common.domain.BaseParams
import com.doctorlist.features.list.model.Item

class LoadNextDoctorListPageParams(val lastKey: String, val currentList: List<Item>) : BaseParams
package com.doctorlist.features.list.model

import com.doctorlist.common.model.Doctor
import java.util.*

sealed class ListItem(val id: String) {
    object RecentDoctorView : ListItem(UUID.randomUUID().toString())
    object VivyDoctorView : ListItem(UUID.randomUUID().toString())

    sealed class DoctorItem(val doctor: Doctor) : ListItem(doctor.id) {
        class VivyDoctorItem(doctor: Doctor) : DoctorItem(doctor)
        class RecentDoctorItem(doctor: Doctor) : DoctorItem(doctor)
    }

    companion object {
        const val RECENT_DOCTOR_VIEW = 1
        const val VIVY_DOCTOR_VIEW = 2
        const val DOCTOR_ITEM = 3
    }
}
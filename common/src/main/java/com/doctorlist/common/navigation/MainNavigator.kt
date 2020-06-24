package com.doctorlist.common.navigation

import com.doctorlist.common.model.Doctor

interface MainNavigator {
    fun navigateToDoctorList()
    fun navigateToDoctorDetails(doctor: Doctor)
}
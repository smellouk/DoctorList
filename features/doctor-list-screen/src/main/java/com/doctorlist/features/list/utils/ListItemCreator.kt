package com.doctorlist.features.list.utils

import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.di.DoctorListScope
import com.doctorlist.features.list.domain.recentdoctors.RecentDoctorDataState
import com.doctorlist.features.list.domain.remotedoctors.RemoteDoctorDataState
import com.doctorlist.features.list.model.Item
import javax.inject.Inject

@DoctorListScope
class ListItemCreator @Inject constructor() {
    fun createFullList(
        recentDoctorsData: RecentDoctorDataState.Success,
        remoteDoctorData: RemoteDoctorDataState.Success
    ): List<Item> = createFullList(recentDoctorsData.list, remoteDoctorData.list)

    fun createFullList(
        recentDoctors: List<Doctor>,
        vivyDoctors: List<Doctor>
    ): List<Item> = mutableListOf<Item>()
        .addRecentDoctorView()
        .addRecentDoctorList(recentDoctors)
        .addVivyDoctorView()
        .addVivyDoctorList(
            vivyDoctors - recentDoctors
        )

    fun createRecentList(
        recentDoctorsData: RecentDoctorDataState.Success
    ): List<Item> = createRecentList(recentDoctorsData.list)

    fun createRecentList(
        recentList: List<Doctor>
    ): List<Item> = mutableListOf<Item>()
        .addRecentDoctorView()
        .addRecentDoctorList(recentList)

    fun createVivyList(
        remoteDoctorData: RemoteDoctorDataState.Success
    ): List<Item> = createVivyList(remoteDoctorData.list)

    fun createVivyList(
        list: List<Doctor>
    ): List<Item> = mutableListOf<Item>()
        .addVivyDoctorView()
        .addVivyDoctorList(list)

    private fun MutableList<Item>.addRecentDoctorView(): MutableList<Item> {
        add(Item.RecentDoctorView)
        return this
    }

    private fun MutableList<Item>.addRecentDoctorList(
        list: List<Doctor>
    ): MutableList<Item> {
        addAll(list.map { doctor ->
            Item.DoctorItem.RecentDoctorItem(doctor)
        })
        return this
    }

    private fun MutableList<Item>.addVivyDoctorList(
        list: List<Doctor>
    ): MutableList<Item> {
        addAll(list.sortedByDescending { doctor ->
            doctor.rating
        }.map { doctor ->
            Item.DoctorItem.VivyDoctorItem(doctor)
        })
        return this
    }

    private fun MutableList<Item>.addVivyDoctorView(): MutableList<Item> {
        add(Item.VivyDoctorView)
        return this
    }
}
package com.doctorlist.features.list

import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.model.Item
import com.doctorlist.features.list.utils.ListItemCreator
import org.junit.Assert.assertTrue
import org.junit.Test

class ListItemCreatorTest {
    private val lisItemCreator = ListItemCreator()

    @Test
    fun createFullList_ShouldCreatePresentationListWithRecentDoctorsAndVivyDoctors() {
        lisItemCreator.createFullList(givenRecentDoctorList, givenVivyDoctorList).run {
            assertTrue(size == expectedFullListSize)
            assertTrue(this[0] is Item.RecentDoctorView)
            assertTrue(this[1] is Item.DoctorItem.RecentDoctorItem)
            assertTrue(this[2] is Item.VivyDoctorView)
            assertTrue(this[3] is Item.DoctorItem.VivyDoctorItem)
        }
    }

    @Test
    fun createRecentList_ShouldCreatePresentationListWithRecentDoctors() {
        lisItemCreator.createRecentList(givenRecentDoctorList).run {
            assertTrue(size == expectedRecentListSize)
            assertTrue(this[0] is Item.RecentDoctorView)
            assertTrue(this[1] is Item.DoctorItem.RecentDoctorItem)
        }
    }

    @Test
    fun createRecentList_ShouldCreatePresentationListWithVivyDoctors() {
        lisItemCreator.createVivyList(givenVivyDoctorList).run {
            assertTrue(size == expectedVivyListSize)
            assertTrue(this[0] is Item.VivyDoctorView)
            assertTrue(this[1] is Item.DoctorItem.VivyDoctorItem)
        }
    }
}

private val givenRecentDoctor = Doctor(
    "1", "", "", "", "", "", "", "", ""
)
private val givenVivyDoctor = Doctor(
    "2", "", "", "", "", "", "", "", ""
)
private val givenRecentDoctorList = listOf(givenRecentDoctor)
private val givenVivyDoctorList = listOf(givenVivyDoctor)
private const val expectedFullListSize = 4
private const val expectedRecentListSize = 2
private const val expectedVivyListSize = 2
package com.doctorlist.features.list

import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.domain.DoctorListDataState
import com.doctorlist.features.list.domain.GetDoctorsUseCase
import com.doctorlist.features.list.domain.recentdoctors.GetRecentDoctorUseCase
import com.doctorlist.features.list.domain.recentdoctors.RecentDoctorDataState
import com.doctorlist.features.list.domain.remotedoctors.GetRemoteDoctorsUseCase
import com.doctorlist.features.list.domain.remotedoctors.RemoteDoctorDataState
import com.doctorlist.features.list.model.Item
import com.doctorlist.features.list.utils.ListItemCreator
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test

class GetDoctorsUseCaseTest : BaseTest() {
    @RelaxedMockK
    lateinit var getRemoteDoctorsUseCase: GetRemoteDoctorsUseCase

    @RelaxedMockK
    lateinit var getRecentDoctorUseCase: GetRecentDoctorUseCase

    @RelaxedMockK
    lateinit var listItemCreator: ListItemCreator

    @InjectMockKs
    lateinit var useCase: GetDoctorsUseCase

    @Test
    fun buildObservable_ShouldEmitFullListItem() {
        every {
            getRemoteDoctorsUseCase.buildObservable()
        } returns Observable.just(givenRemoteDoctorDataState)

        every {
            getRecentDoctorUseCase.buildObservable()
        } returns Observable.just(givenRecentDoctorDataState)

        every {
            listItemCreator.createFullList(givenRecentDoctorDataState, givenRemoteDoctorDataState)
        } returns anyListOfItems

        useCase.buildObservable()
            .test()
            .run {
                assertComplete()
                assertNoErrors()
                assertValueCount(1)
                assertValue { dataState ->
                    dataState is DoctorListDataState.Success
                            && dataState.list == anyListOfItems
                }
            }

        verify(exactly = once) {
            getRemoteDoctorsUseCase.buildObservable()
            getRecentDoctorUseCase.buildObservable()
            listItemCreator.createFullList(givenRecentDoctorDataState, givenRemoteDoctorDataState)
        }
    }
}

private val givenRemoteDoctor = Doctor(
    "1", "", "", "", "", "", "", "", ""
)
private val givenRecentDoctor = Doctor(
    "1", "", "", "", "", "", "", "", ""
)
private const val givenPage = "givenPage"
private val listOfRemoteDoctors = listOf(givenRemoteDoctor)
private val listOfRecentDoctors = listOf(givenRecentDoctor)
private val givenRemoteDoctorDataState = RemoteDoctorDataState.Success(
    listOfRemoteDoctors, givenPage
)
private val givenRecentDoctorDataState = RecentDoctorDataState.Success(listOfRecentDoctors)
private val anyListOfItems = listOf(Item.VivyDoctorView)
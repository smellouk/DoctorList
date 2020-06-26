package com.doctorlist.features.list

import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.domain.LoadNextDoctorListPageParams
import com.doctorlist.features.list.domain.remotedoctors.LoadNextRemoteDoctorListPageUseCase
import com.doctorlist.features.list.domain.remotedoctors.RemoteDoctorDataState
import com.doctorlist.features.list.model.Item
import com.doctorlist.features.list.utils.DoctorMapper
import com.doctorlist.repositories.remote.dto.DoctorDto
import com.doctorlist.repositories.remote.dto.Response
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test

class LoadNextRemoteDoctorListPageUseCaseTest : BaseTest() {
    @RelaxedMockK
    lateinit var remoteDoctorsRepository: RemoteDoctorsRepository

    @RelaxedMockK
    lateinit var doctorMapper: DoctorMapper

    @InjectMockKs
    lateinit var useCaseRemote: LoadNextRemoteDoctorListPageUseCase

    @Test
    fun buildObservable_ShouldEmitSuccessfulDataStateWhenListIsNotEmpty() {
        every {
            remoteDoctorsRepository.getDoctors(givenLastKey)
        } returns Single.just(response)

        every {
            doctorMapper.map(response)
        } returns listOfDoctors

        useCaseRemote.buildObservable(givenParams)
            .test()
            .run {
                assertComplete()
                assertNoErrors()
                assertValueCount(1)
                assertValue { dataState ->
                    dataState is RemoteDoctorDataState.Success
                            && dataState.list == listOfDoctors
                }
            }

        verify(exactly = once) {
            remoteDoctorsRepository.getDoctors(givenLastKey)
            doctorMapper.map(response)
        }
    }

    @Test
    fun buildObservable_ShouldEmitEmptyDataStateWhenListIsEmpty() {
        every {
            remoteDoctorsRepository.getDoctors(givenLastKey)
        } returns Single.just(emptyResponse)

        every {
            doctorMapper.map(emptyResponse)
        } returns emptyList()

        useCaseRemote.buildObservable(givenParams)
            .test()
            .run {
                assertComplete()
                assertNoErrors()
                assertValueCount(1)
                assertValue { dataState ->
                    dataState is RemoteDoctorDataState.Empty
                }
            }

        verify(exactly = once) {
            remoteDoctorsRepository.getDoctors(givenLastKey)
            doctorMapper.map(emptyResponse)
        }
    }
}

private const val givenLastKey = "givenLastKey"
private val givenList = emptyList<Item>()
private val givenParams = LoadNextDoctorListPageParams(givenLastKey, givenList)

private val givenDto = DoctorDto()
private val listDto = listOf(givenDto)

private val givenDoctor = Doctor(
    "", "", "", "", "", "", "", "", ""
)
private val listOfDoctors = listOf(givenDoctor)

private val response = Response(listDto)
private val emptyResponse = Response()
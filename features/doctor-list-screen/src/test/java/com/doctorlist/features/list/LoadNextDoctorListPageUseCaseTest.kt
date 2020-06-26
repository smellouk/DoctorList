package com.doctorlist.features.list

import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.domain.getdoctors.DoctorDataState
import com.doctorlist.features.list.domain.getdoctors.DoctorMapper
import com.doctorlist.features.list.domain.getdoctors.LoadNextDoctorListPageParams
import com.doctorlist.features.list.domain.getdoctors.LoadNextDoctorListPageUseCase
import com.doctorlist.repositories.remote.dto.DoctorDto
import com.doctorlist.repositories.remote.dto.Response
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import org.junit.Test

class LoadNextDoctorListPageUseCaseTest : BaseTest() {
    @RelaxedMockK
    lateinit var remoteDoctorsRepository: RemoteDoctorsRepository

    @RelaxedMockK
    lateinit var doctorMapper: DoctorMapper

    @InjectMockKs
    lateinit var useCase: LoadNextDoctorListPageUseCase

    @Test
    fun buildObservable_ShouldEmitSuccessfulDataStateWhenListIsNotEmpty() {
        every {
            remoteDoctorsRepository.getDoctors(givenLastKey)
        } returns Single.just(response)

        every {
            doctorMapper.map(response)
        } returns listOfDoctors

        useCase.buildObservable(givenParams)
            .test()
            .run {
                assertComplete()
                assertNoErrors()
                assertValueCount(1)
                assertValue { dataState ->
                    dataState is DoctorDataState.Success
                            && dataState.list == listOfDoctors
                }
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

        useCase.buildObservable(givenParams)
            .test()
            .run {
                assertComplete()
                assertNoErrors()
                assertValueCount(1)
                assertValue { dataState ->
                    dataState is DoctorDataState.Empty
                }
            }
    }
}

private const val givenLastKey = "givenLastKey"
private val givenParams = LoadNextDoctorListPageParams(givenLastKey)

private val givenDto = DoctorDto()
private val listDto = listOf(givenDto)

private val givenDoctor = Doctor(
    "", "", "", 0.0, "", 0, "", "", ""
)
private val listOfDoctors = listOf(givenDoctor)

private val response = Response(listDto)
private val emptyResponse = Response()
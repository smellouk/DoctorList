package com.doctorlist.repositories.remote

import com.doctorlist.repositories.remote.dto.DoctorDto
import com.doctorlist.repositories.remote.dto.Response
import com.doctorlist.repositories.remote.network.DoctorsService
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class RemoteDoctorsRepositoryTest {
    @RelaxedMockK
    lateinit var doctorsService: DoctorsService

    @InjectMockKs
    lateinit var repository: RemoteDoctorsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getDoctors_ShouldReturnValidListOfDoctors() {
        every {
            doctorsService.getDoctors()
        } returns Single.just(response)

        repository.getDoctors()
            .test()
            .run {
                assertComplete()
                assertNoErrors()
                assertValueCount(1)
                assertValue { response ->
                    response.doctors?.size == 1
                }
            }

        verify(exactly = once) {
            doctorsService.getDoctors()
        }

        verify(exactly = none) {
            doctorsService.getDoctors(any())
        }
    }

    @Test
    fun getDoctorsWithLastKey_ShouldReturnValidNextListOfDoctors() {
        every {
            doctorsService.getDoctors(lastKey)
        } returns Single.just(response)

        repository.getDoctors(lastKey)
            .test()
            .run {
                assertComplete()
                assertNoErrors()
                assertValueCount(1)
                assertValue { response ->
                    response.doctors?.size == 1
                }
            }

        verify(exactly = once) {
            doctorsService.getDoctors(lastKey)
        }

        verify(exactly = none) {
            doctorsService.getDoctors()
        }
    }
}

private const val once = 1
private const val none = 0
private const val lastKey = "9821378912"
private val doctor = DoctorDto()
private val response = Response(doctors = listOf(doctor))
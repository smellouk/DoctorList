package com.doctorlist.features.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.domain.DoctorListDataState
import com.doctorlist.features.list.domain.GetDoctorsUseCase
import com.doctorlist.features.list.domain.LoadNextDoctorListPageUseCase
import com.doctorlist.features.list.domain.recentdoctors.AddDoctorToRecentParams
import com.doctorlist.features.list.domain.recentdoctors.AddDoctorToRecentUseCase
import com.doctorlist.features.list.domain.refreshlist.RefreshListWhenItemClickUseCase
import com.doctorlist.features.list.model.Item
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class DoctorListViewModelTest : BaseTest() {
    private val testScheduler = TestScheduler()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule(testScheduler)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var getDoctorsUseCase: GetDoctorsUseCase

    @RelaxedMockK
    lateinit var loadNextDoctorListPageUseCase: LoadNextDoctorListPageUseCase

    @RelaxedMockK
    lateinit var addDoctorToRecentUseCase: AddDoctorToRecentUseCase

    @RelaxedMockK
    lateinit var refreshListWhenItemClickUseCase: RefreshListWhenItemClickUseCase

    @RelaxedMockK
    lateinit var viewStateMapper: ViewStateMapper

    @InjectMockKs
    lateinit var viewModel: DoctorListViewModel

    @Test
    fun onGetDoctorsCommand_shouldGetDoctors() {
        every {
            getDoctorsUseCase.buildObservable()
        } returns Observable.just(givenDoctorListDataState)

        every {
            viewStateMapper.map(givenDoctorListDataState)
        } returns givenDoctorListReadyState

        viewModel.onCommand(Command.GetDoctors)
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        verify(exactly = once) {
            getDoctorsUseCase.buildObservable()
            viewStateMapper.map(givenDoctorListDataState)
        }
        assertTrue(viewModel.liveData.value == givenDoctorListReadyState)
    }


    @Test
    fun onNavigateToDetails_ShouldSaveDataAndNavigateToScreenDetails() {
        val addDoctorToRecentParamsSlot = slot<AddDoctorToRecentParams>()
        every {
            addDoctorToRecentUseCase.buildObservable(capture(addDoctorToRecentParamsSlot))
        } returns Completable.complete()

        viewModel.onCommand(Command.NavigateToDetails(givenItem))
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        verify(exactly = once) {
            addDoctorToRecentUseCase.buildObservable(any())
        }

        assertTrue(addDoctorToRecentParamsSlot.captured.doctor == givenDoctor)
        with(viewModel.liveData) {
            assertTrue(
                value is ViewState.DoctorDetails &&
                        (value as ViewState.DoctorDetails).doctor == givenDoctor
            )
        }
    }
}

private val givenDoctor = Doctor(
    "", "", "", "", "", "", "", "", ""
)
private val givenItem = Item.DoctorItem.VivyDoctorItem(givenDoctor)
private val givenList = listOf(givenItem)
private const val givenPage = "givenPage"
private val givenDoctorListDataState = DoctorListDataState.Success(givenList, givenPage)
private val givenDoctorListReadyState = ViewState.DoctorListReady(givenList, givenPage)
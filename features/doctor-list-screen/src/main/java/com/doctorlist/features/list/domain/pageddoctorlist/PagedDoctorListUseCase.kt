package com.doctorlist.features.list.domain.pageddoctorlist

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.doctorlist.features.list.domain.getdoctors.GetDoctorsUseCase
import com.doctorlist.features.list.domain.getdoctors.LoadNextDoctorListPageUseCase
import com.doctorlist.features.list.domain.recentdoctors.GetRecentDoctorUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class PagedDoctorListUseCase(
    getDoctorsUseCase: GetDoctorsUseCase,
    loadNextDoctorListPageUseCase: LoadNextDoctorListPageUseCase,
    getRecentDoctorUseCase: GetRecentDoctorUseCase,
    compositeDisposable: CompositeDisposable
) {
    private val requestStateSubject = BehaviorSubject.create<RequestState>()

    private val sourceFactory =
        DoctorDataSourceFactory(
            getDoctorsUseCase,
            loadNextDoctorListPageUseCase,
            getRecentDoctorUseCase,
            compositeDisposable,
            requestStateSubject
        )

    private val config = PagedList.Config
        .Builder()
        .setPageSize(PAGE_SIZE)
        .build()

    private val doctorListObservable = RxPagedListBuilder(sourceFactory, config)
        .buildObservable()

    fun buildListObservable(): Observable<PagedDoctorDataState.Success> =
        doctorListObservable.map { doctorList ->
            PagedDoctorDataState.Success(
                doctorList
            )
        }

    fun buildRequestObservable(): Observable<RequestState> = requestStateSubject
}

private const val PAGE_SIZE = 20
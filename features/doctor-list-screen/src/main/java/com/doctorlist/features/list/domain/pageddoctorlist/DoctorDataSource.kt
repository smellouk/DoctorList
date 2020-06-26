package com.doctorlist.features.list.domain.pageddoctorlist

import androidx.paging.PageKeyedDataSource
import com.doctorlist.common.exhaustive
import com.doctorlist.common.model.Doctor
import com.doctorlist.features.list.domain.getdoctors.DoctorDataState
import com.doctorlist.features.list.domain.getdoctors.GetDoctorsUseCase
import com.doctorlist.features.list.domain.getdoctors.LoadNextDoctorListPageParams
import com.doctorlist.features.list.domain.getdoctors.LoadNextDoctorListPageUseCase
import com.doctorlist.features.list.domain.recentdoctors.GetRecentDoctorUseCase
import com.doctorlist.features.list.domain.recentdoctors.RecentDoctorDataState
import com.doctorlist.features.list.model.ListItem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class DoctorDataSource(
    private val getDoctorsUseCase: GetDoctorsUseCase,
    private val loadNextDoctorListPageUseCase: LoadNextDoctorListPageUseCase,
    private val getRecentDoctorUseCase: GetRecentDoctorUseCase,
    private val compositeDisposable: CompositeDisposable,
    private val requestStateSubject: BehaviorSubject<RequestState>
) : PageKeyedDataSource<String, ListItem>() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String?, ListItem>
    ) {
        requestStateSubject.onNext(RequestState.Loading)
        compositeDisposable.add(
            getDoctorsUseCase
                .buildObservable()
                .zipWithRecentDoctors()
                .subscribeOn(Schedulers.io())
                .subscribe({ zipResult ->
                    requestStateSubject.onNext(RequestState.StopLoading)
                    if (zipResult.first.isEmpty()) {
                        requestStateSubject.onNext(RequestState.Fail())
                    } else {
                        callback.onResult(
                            zipResult.first,
                            null,
                            zipResult.second
                        )
                    }
                }, { throwable ->
                    requestStateSubject.onNext(RequestState.Fail(throwable.message))
                })
        )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, ListItem>) {
        requestStateSubject.onNext(RequestState.Loading)
        compositeDisposable.add(
            loadNextDoctorListPageUseCase
                .buildObservable(LoadNextDoctorListPageParams(params.key))
                .subscribeOn(Schedulers.io())
                .subscribe({ dataState ->
                    requestStateSubject.onNext(RequestState.StopLoading)
                    when (dataState) {
                        is DoctorDataState.Success -> {
                            callback.onResult(
                                dataState.list.map { doctor ->
                                    ListItem.DoctorItem.VivyDoctorItem(doctor)
                                },
                                dataState.nextPage
                            )
                        }
                        is DoctorDataState.Empty -> {
                            requestStateSubject.onNext(RequestState.Fail())
                        }
                    }.exhaustive
                }, { throwable ->
                    requestStateSubject.onNext(RequestState.Fail(throwable.message))
                })
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, ListItem>) {
    }

    private fun Observable<DoctorDataState>.zipWithRecentDoctors() = zipWith(
        getRecentDoctorUseCase.buildObservable(),
        BiFunction<DoctorDataState, RecentDoctorDataState, ZipResult> { doctorData, recentDoctorsData ->
            when {
                recentDoctorsData is RecentDoctorDataState.Success &&
                        doctorData is DoctorDataState.Success -> {
                    ZipResult(
                        createFullList(recentDoctorsData, doctorData),
                        doctorData.nextPage
                    )
                }
                recentDoctorsData is RecentDoctorDataState.Success &&
                        doctorData is DoctorDataState.Empty -> {
                    ZipResult(
                        createRecentList(recentDoctorsData),
                        null
                    )
                }
                recentDoctorsData is RecentDoctorDataState.Empty &&
                        doctorData is DoctorDataState.Success -> {
                    ZipResult(
                        createVivyList(doctorData),
                        doctorData.nextPage
                    )
                }
                else -> {
                    ZipResult(emptyList(), null)
                }
            }
        }
    )

    private fun createFullList(
        recentDoctorsData: RecentDoctorDataState.Success,
        doctorData: DoctorDataState.Success
    ): List<ListItem> = mutableListOf<ListItem>()
        .addRecentDoctorView()
        .addRecentDoctorList(recentDoctorsData.list)
        .addVivyDoctorView()
        .addVivyDoctorList(doctorData.list)

    private fun createRecentList(
        recentDoctorsData: RecentDoctorDataState.Success
    ): List<ListItem> = mutableListOf<ListItem>()
        .addRecentDoctorView()
        .addRecentDoctorList(recentDoctorsData.list)

    private fun createVivyList(
        doctorData: DoctorDataState.Success
    ): List<ListItem> = mutableListOf<ListItem>()
        .addVivyDoctorView()
        .addVivyDoctorList(doctorData.list)

    private fun MutableList<ListItem>.addRecentDoctorView(): MutableList<ListItem> {
        add(ListItem.RecentDoctorView)
        return this
    }

    private fun MutableList<ListItem>.addRecentDoctorList(list: List<Doctor>): MutableList<ListItem> {
        addAll(list.map { doctor ->
            ListItem.DoctorItem.RecentDoctorItem(doctor)
        })
        return this
    }

    private fun MutableList<ListItem>.addVivyDoctorList(list: List<Doctor>): MutableList<ListItem> {
        addAll(list.map { doctor ->
            ListItem.DoctorItem.VivyDoctorItem(doctor)
        })
        return this
    }

    private fun MutableList<ListItem>.addVivyDoctorView(): MutableList<ListItem> {
        add(ListItem.VivyDoctorView)
        return this
    }
}

typealias ZipResult = Pair<List<ListItem>, String?>
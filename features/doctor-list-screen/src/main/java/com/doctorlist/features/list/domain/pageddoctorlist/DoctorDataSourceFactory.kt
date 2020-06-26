package com.doctorlist.features.list.domain.pageddoctorlist

import androidx.paging.DataSource
import com.doctorlist.features.list.domain.getdoctors.GetDoctorsUseCase
import com.doctorlist.features.list.domain.getdoctors.LoadNextDoctorListPageUseCase
import com.doctorlist.features.list.domain.recentdoctors.GetRecentDoctorUseCase
import com.doctorlist.features.list.model.ListItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class DoctorDataSourceFactory(
    private val getDoctorsUseCase: GetDoctorsUseCase,
    private val loadNextDoctorListPageUseCase: LoadNextDoctorListPageUseCase,
    private val getRecentDoctorUseCase: GetRecentDoctorUseCase,
    private val compositeDisposable: CompositeDisposable,
    private val requestStateSubject: BehaviorSubject<RequestState>
) : DataSource.Factory<String, ListItem>() {
    override fun create(): DataSource<String, ListItem> =
        DoctorDataSource(
            getDoctorsUseCase,
            loadNextDoctorListPageUseCase,
            getRecentDoctorUseCase,
            compositeDisposable,
            requestStateSubject
        )
}
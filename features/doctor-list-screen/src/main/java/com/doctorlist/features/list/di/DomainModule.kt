package com.doctorlist.features.list.di

import com.doctorlist.features.list.domain.getdoctors.DoctorMapper
import com.doctorlist.features.list.domain.getdoctors.GetDoctorsUseCase
import com.doctorlist.features.list.domain.getdoctors.LoadNextDoctorListPageUseCase
import com.doctorlist.features.list.domain.pageddoctorlist.PagedDoctorListUseCase
import com.doctorlist.features.list.domain.recentdoctors.AddDoctorToRecentUseCase
import com.doctorlist.features.list.domain.recentdoctors.DoctorEntityMapper
import com.doctorlist.features.list.domain.recentdoctors.GetRecentDoctorUseCase
import com.doctorlist.repositories.offline.db.RecentVisitedDoctorsRepository
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module
class DomainModule {
    @DoctorListScope
    @Provides
    @Named(PAGED_DOCTOR_LIST_COMPOSITE_DISPOSABLE)
    fun providePagedDoctorListCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @DoctorListScope
    @Provides
    fun provideDoctorMapper() = DoctorMapper()

    @DoctorListScope
    @Provides
    fun provideDoctorEntityMapper() = DoctorEntityMapper()

    @DoctorListScope
    @Provides
    fun provideGetDoctorsUseCase(
        remoteDoctorsRepository: RemoteDoctorsRepository,
        doctorMapper: DoctorMapper
    ) = GetDoctorsUseCase(remoteDoctorsRepository, doctorMapper)

    @DoctorListScope
    @Provides
    fun provideLoadNextDoctorListPage(
        remoteDoctorsRepository: RemoteDoctorsRepository,
        doctorMapper: DoctorMapper
    ) = LoadNextDoctorListPageUseCase(remoteDoctorsRepository, doctorMapper)

    @DoctorListScope
    @Provides
    fun provideGetRecentDoctorUseCase(
        recentDoctorsRepository: RecentVisitedDoctorsRepository,
        doctorMapper: DoctorMapper
    ) = GetRecentDoctorUseCase(
        recentDoctorsRepository,
        doctorMapper
    )

    @DoctorListScope
    @Provides
    fun provideAddDoctorToRecentUseCase(
        recentDoctorsRepository: RecentVisitedDoctorsRepository,
        doctorEntityMapper: DoctorEntityMapper
    ) = AddDoctorToRecentUseCase(
        recentDoctorsRepository,
        doctorEntityMapper
    )

    @DoctorListScope
    @Provides
    fun providePagedDoctorListUseCase(
        getDoctorsUseCase: GetDoctorsUseCase,
        loadNextDoctorListPageUseCase: LoadNextDoctorListPageUseCase,
        getRecentDoctorUseCase: GetRecentDoctorUseCase,
        @Named(PAGED_DOCTOR_LIST_COMPOSITE_DISPOSABLE)
        compositeDisposable: CompositeDisposable
    ) = PagedDoctorListUseCase(
        getDoctorsUseCase,
        loadNextDoctorListPageUseCase,
        getRecentDoctorUseCase,
        compositeDisposable
    )
}

const val PAGED_DOCTOR_LIST_COMPOSITE_DISPOSABLE = "PAGED_DOCTOR_LIST_COMPOSITE_DISPOSABLE"
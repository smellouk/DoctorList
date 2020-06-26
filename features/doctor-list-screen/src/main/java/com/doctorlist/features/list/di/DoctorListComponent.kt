package com.doctorlist.features.list.di

import com.doctorlist.common.base.BaseComponentProvider
import com.doctorlist.features.list.DoctorListFragment
import dagger.Subcomponent

@DoctorListScope
@Subcomponent(modules = [DoctorListViewModelModule::class, DomainModule::class])
interface DoctorListComponent {
    fun inject(fragment: DoctorListFragment)
}

interface DoctorListComponentProvider : BaseComponentProvider {
    fun getDoctorListComponent(): DoctorListComponent
}
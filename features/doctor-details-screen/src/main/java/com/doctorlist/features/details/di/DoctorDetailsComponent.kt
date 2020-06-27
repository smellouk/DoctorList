package com.doctorlist.features.details.di

import com.doctorlist.common.base.BaseComponentProvider
import com.doctorlist.features.details.DoctorDetailsFragment
import dagger.Subcomponent

@DoctorDetailsScope
@Subcomponent(modules = [DoctorDetailsViewModelModule::class])
interface DoctorDetailsComponent {
    fun inject(fragment: DoctorDetailsFragment)
}

interface DoctorDetailsComponentProvider : BaseComponentProvider {
    fun getDoctorDetailsComponent(): DoctorDetailsComponent
}
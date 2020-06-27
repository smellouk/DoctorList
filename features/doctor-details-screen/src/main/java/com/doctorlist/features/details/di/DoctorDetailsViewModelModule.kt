package com.doctorlist.features.details.di

import androidx.lifecycle.ViewModel
import com.doctorlist.common.di.ViewModelKey
import com.doctorlist.features.details.DoctorDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DoctorDetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DoctorDetailsViewModel::class)
    @DoctorDetailsScope
    fun bindViewModel(viewModel: DoctorDetailsViewModel): ViewModel
}
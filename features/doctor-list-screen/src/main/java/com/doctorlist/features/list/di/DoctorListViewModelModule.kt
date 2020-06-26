package com.doctorlist.features.list.di

import androidx.lifecycle.ViewModel
import com.doctorlist.common.di.ViewModelKey
import com.doctorlist.features.list.DoctorListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DoctorListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DoctorListViewModel::class)
    @DoctorListScope
    fun bindViewModel(viewModel: DoctorListViewModel): ViewModel
}
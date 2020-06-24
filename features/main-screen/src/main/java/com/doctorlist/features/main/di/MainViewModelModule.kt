package com.doctorlist.features.main.di

import androidx.lifecycle.ViewModel
import com.doctorlist.common.di.ViewModelKey
import com.doctorlist.features.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @MainScope
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
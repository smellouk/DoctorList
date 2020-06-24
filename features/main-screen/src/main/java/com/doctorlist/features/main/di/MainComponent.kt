package com.doctorlist.features.main.di

import com.doctorlist.common.base.BaseComponentProvider
import com.doctorlist.features.main.MainActivity
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [MainViewModelModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}

interface MainComponentProvider : BaseComponentProvider {
    fun getMainComponent(): MainComponent
}
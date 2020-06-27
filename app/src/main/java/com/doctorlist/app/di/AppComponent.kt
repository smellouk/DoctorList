package com.doctorlist.app.di

import com.doctorlist.features.details.di.DoctorDetailsComponent
import com.doctorlist.features.list.di.DoctorListComponent
import com.doctorlist.features.main.di.MainComponent
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, DomainModule::class])
interface AppComponent {
    fun getMainComponent(): MainComponent

    fun getDoctorListComponent(): DoctorListComponent

    fun getDoctorDetailsComponent(): DoctorDetailsComponent
}
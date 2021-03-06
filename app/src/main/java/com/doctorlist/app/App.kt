package com.doctorlist.app

import android.app.Application
import com.doctorlist.app.di.AppComponent
import com.doctorlist.app.di.AppModule
import com.doctorlist.app.di.DaggerAppComponent
import com.doctorlist.app.di.DomainModule
import com.doctorlist.features.details.di.DoctorDetailsComponent
import com.doctorlist.features.details.di.DoctorDetailsComponentProvider
import com.doctorlist.features.list.di.DoctorListComponent
import com.doctorlist.features.list.di.DoctorListComponentProvider
import com.doctorlist.features.main.di.MainComponent
import com.doctorlist.features.main.di.MainComponentProvider

class App : Application(), MainComponentProvider, DoctorListComponentProvider,
    DoctorDetailsComponentProvider {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .domainModule(DomainModule(BuildConfig.HOST_URL, BuildConfig.DEBUG))
            .build()
    }

    override fun getMainComponent(): MainComponent =
        appComponent.getMainComponent()

    override fun getDoctorListComponent(): DoctorListComponent =
        appComponent.getDoctorListComponent()

    override fun getDoctorDetailsComponent(): DoctorDetailsComponent =
        appComponent.getDoctorDetailsComponent()
}
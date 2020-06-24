package com.doctorlist.app

import android.app.Application
import com.doctorlist.app.di.AppComponent
import com.doctorlist.app.di.AppModule
import com.doctorlist.app.di.DaggerAppComponent
import com.doctorlist.app.di.DomainModule
import com.doctorlist.features.main.di.MainComponent
import com.doctorlist.features.main.di.MainComponentProvider

class App : Application(), MainComponentProvider {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .domainModule(DomainModule(BuildConfig.HOST_URL, BuildConfig.DEBUG))
            .build()
    }

    override fun getMainComponent(): MainComponent = appComponent.getMainComponent()
}
package com.doctorlist.repositories.remote.di.module

import com.doctorlist.repositories.remote.network.DoctorsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ServiceModule {
    @Provides
    @Singleton
    fun provideRatesService(retrofit: Retrofit): DoctorsService =
        retrofit.create(DoctorsService::class.java)
}
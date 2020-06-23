package com.doctorlist.repositories.remote.di.module

import com.doctorlist.repositories.remote.network.DoctorsService
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRatesRepository(service: DoctorsService): RemoteDoctorsRepository =
        RemoteDoctorsRepository(service)
}
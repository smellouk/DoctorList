package com.doctorlist.repositories.offline.di

import com.doctorlist.repositories.offline.OfflineRepositories
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, DatabaseModule::class])
interface OfflineRepositoriesComponent {
    fun inject(offlineRepositories: OfflineRepositories)
}
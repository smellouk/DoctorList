package com.doctorlist.repositories.remote.di

import com.doctorlist.repositories.remote.RemoteRepositories
import com.doctorlist.repositories.remote.di.module.ClientModule
import com.doctorlist.repositories.remote.di.module.RepositoryModule
import com.doctorlist.repositories.remote.di.module.ServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ClientModule::class, ServiceModule::class, RepositoryModule::class])
interface RemoteRepositoriesComponent {
    fun inject(repositories: RemoteRepositories)
}
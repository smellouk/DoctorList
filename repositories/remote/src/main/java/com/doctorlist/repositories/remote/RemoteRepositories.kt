package com.doctorlist.repositories.remote

import com.doctorlist.repositories.remote.di.DaggerRemoteRepositoriesComponent
import com.doctorlist.repositories.remote.di.module.ClientModule
import com.doctorlist.repositories.remote.network.RemoteDoctorsRepository
import okhttp3.Interceptor
import javax.inject.Inject

class RemoteRepositories(
    hostUrl: String,
    isDebug: Boolean = false,
    debugInterceptors: List<Interceptor> = emptyList()
) {
    @Inject
    lateinit var remoteDoctorsRepository: RemoteDoctorsRepository

    init {
        DaggerRemoteRepositoriesComponent.builder()
            .clientModule(ClientModule(hostUrl, isDebug, debugInterceptors))
            .build()
            .apply {
                inject(this@RemoteRepositories)
            }
    }
}
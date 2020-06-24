package com.doctorlist.app.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.doctorlist.repositories.remote.RemoteRepositories
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

@Module
class DomainModule(private val hostUrl: String, private val isDebug: Boolean) {
    @AppScope
    @Provides
    fun provideRemoteRepositories(
        context: Context,
        chuckerInterceptor: ChuckerInterceptor
    ): RemoteRepositories =
        RemoteRepositories(
            hostUrl,
            isDebug = isDebug,
            debugInterceptors = listOf<Interceptor>(chuckerInterceptor)
        )

    @AppScope
    @Provides
    fun provideDoctorsRepository(repositories: RemoteRepositories) =
        repositories.remoteDoctorsRepository
}
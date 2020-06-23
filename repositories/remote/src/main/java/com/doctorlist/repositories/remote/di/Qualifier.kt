package com.doctorlist.repositories.remote.di

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
internal annotation class HostUrlInfo

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
internal annotation class NetworkTimeOutInfo

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
internal annotation class RetryCountInfo
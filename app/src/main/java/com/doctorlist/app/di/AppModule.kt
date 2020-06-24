package com.doctorlist.app.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides

@Module
class AppModule(
    private val context: Context
) {
    @AppScope
    @Provides
    fun provideContext(): Context = context

    @AppScope
    @Provides
    fun provideChucker(): ChuckerInterceptor = ChuckerInterceptor(context)
}
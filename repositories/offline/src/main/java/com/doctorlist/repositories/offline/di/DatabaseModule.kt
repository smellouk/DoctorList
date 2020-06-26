package com.doctorlist.repositories.offline.di

import android.content.Context
import androidx.room.Room
import com.doctorlist.repositories.offline.dao.RecentDoctorDao
import com.doctorlist.repositories.offline.db.OfflineDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideDataBase(context: Context): OfflineDataBase = Room.databaseBuilder(
        context,
        OfflineDataBase::class.java,
        "offline.db"
    ).build()

    @Provides
    @Singleton
    fun provideRecentDoctorDao(db: OfflineDataBase): RecentDoctorDao = db.recentDoctorCompany()
}
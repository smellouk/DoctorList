package com.doctorlist.repositories.offline.di

import com.doctorlist.repositories.offline.dao.RecentDoctorDao
import com.doctorlist.repositories.offline.db.RecentVisitedDoctorsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRecentVisitedRepository(recentDoctorDao: RecentDoctorDao) =
        RecentVisitedDoctorsRepository(recentDoctorDao)
}
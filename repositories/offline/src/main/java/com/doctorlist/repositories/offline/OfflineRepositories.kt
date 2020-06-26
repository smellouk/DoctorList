package com.doctorlist.repositories.offline

import android.content.Context
import com.doctorlist.repositories.offline.db.RecentVisitedDoctorsRepository
import com.doctorlist.repositories.offline.di.DaggerOfflineRepositoriesComponent
import com.doctorlist.repositories.offline.di.DatabaseModule
import javax.inject.Inject

class OfflineRepositories(context: Context) {
    @Inject
    lateinit var recentVisitedDoctorsRepository: RecentVisitedDoctorsRepository

    init {
        DaggerOfflineRepositoriesComponent.builder()
            .databaseModule(DatabaseModule(context))
            .build()
            .run {
                inject(this@OfflineRepositories)
            }
    }
}
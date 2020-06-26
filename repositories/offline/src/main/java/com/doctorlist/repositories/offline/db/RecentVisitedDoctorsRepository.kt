package com.doctorlist.repositories.offline.db

import com.doctorlist.repositories.offline.dao.RecentDoctorDao
import com.doctorlist.repositories.offline.entity.DoctorEntity
import io.reactivex.Completable
import io.reactivex.Maybe

class RecentVisitedDoctorsRepository(
    private val recentDoctorDao: RecentDoctorDao
) {
    fun getRecent(limit: Int = 3): Maybe<List<DoctorEntity>> = recentDoctorDao.getRecent(limit)

    fun insert(entity: DoctorEntity): Completable = recentDoctorDao.insert(entity)

    fun insert(entities: List<DoctorEntity>): Completable = recentDoctorDao.insert(entities)

    fun clear(): Completable = recentDoctorDao.clear()
}
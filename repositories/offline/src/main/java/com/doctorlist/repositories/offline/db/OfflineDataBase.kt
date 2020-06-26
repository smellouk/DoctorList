package com.doctorlist.repositories.offline.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.doctorlist.repositories.offline.dao.RecentDoctorDao
import com.doctorlist.repositories.offline.entity.DoctorEntity

@Database(entities = [DoctorEntity::class], version = 1)
abstract class OfflineDataBase : RoomDatabase() {
    abstract fun recentDoctorCompany(): RecentDoctorDao
}
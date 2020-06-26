package com.doctorlist.repositories.offline.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doctorlist.repositories.offline.entity.DoctorEntity
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface RecentDoctorDao {
    @Query("SELECT * FROM recent_doctors WHERE id = :id")
    fun getById(id: String): Maybe<DoctorEntity>

    @Query("SELECT * FROM recent_doctors ORDER BY date DESC LIMIT :limit")
    fun getRecent(limit: Int): Maybe<List<DoctorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: DoctorEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<DoctorEntity>): Completable

    @Query("DELETE FROM recent_doctors")
    fun clear(): Completable
}
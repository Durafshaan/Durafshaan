package com.prayertimes.app.data.database

import androidx.room.*
import androidx.lifecycle.LiveData
import com.prayertimes.app.data.models.PrayerTime
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerTimeDao {
    
    @Query("SELECT * FROM prayer_times WHERE date = :date LIMIT 1")
    suspend fun getPrayerTimeByDate(date: String): PrayerTime?
    
    @Query("SELECT * FROM prayer_times WHERE date = :date LIMIT 1")
    fun getPrayerTimeByDateLiveData(date: String): LiveData<PrayerTime?>
    
    @Query("SELECT * FROM prayer_times ORDER BY date DESC")
    fun getAllPrayerTimes(): Flow<List<PrayerTime>>
    
    @Query("SELECT * FROM prayer_times WHERE date >= :startDate AND date <= :endDate ORDER BY date ASC")
    suspend fun getPrayerTimesInRange(startDate: String, endDate: String): List<PrayerTime>
    
    @Query("SELECT * FROM prayer_times WHERE isManuallySet = 1 ORDER BY date DESC")
    fun getManuallySetPrayerTimes(): Flow<List<PrayerTime>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayerTime(prayerTime: PrayerTime): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayerTimes(prayerTimes: List<PrayerTime>)
    
    @Update
    suspend fun updatePrayerTime(prayerTime: PrayerTime)
    
    @Delete
    suspend fun deletePrayerTime(prayerTime: PrayerTime)
    
    @Query("DELETE FROM prayer_times WHERE date = :date")
    suspend fun deletePrayerTimeByDate(date: String)
    
    @Query("DELETE FROM prayer_times WHERE date < :date")
    suspend fun deleteOldPrayerTimes(date: String)
    
    @Query("SELECT COUNT(*) FROM prayer_times")
    suspend fun getPrayerTimesCount(): Int
}

package com.prayertimes.app.data.database

import androidx.room.*
import androidx.lifecycle.LiveData
import com.prayertimes.app.data.models.AzanTime
import kotlinx.coroutines.flow.Flow

@Dao
interface AzanTimeDao {
    
    @Query("SELECT * FROM azan_times WHERE date = :date LIMIT 1")
    suspend fun getAzanTimeByDate(date: String): AzanTime?
    
    @Query("SELECT * FROM azan_times WHERE date = :date LIMIT 1")
    fun getAzanTimeByDateLiveData(date: String): LiveData<AzanTime?>
    
    @Query("SELECT * FROM azan_times ORDER BY date DESC")
    fun getAllAzanTimes(): Flow<List<AzanTime>>
    
    @Query("SELECT * FROM azan_times WHERE date >= :startDate AND date <= :endDate ORDER BY date ASC")
    suspend fun getAzanTimesInRange(startDate: String, endDate: String): List<AzanTime>
    
    @Query("SELECT * FROM azan_times WHERE isManuallySet = 1 ORDER BY date DESC")
    fun getManuallySetAzanTimes(): Flow<List<AzanTime>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAzanTime(azanTime: AzanTime): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAzanTimes(azanTimes: List<AzanTime>)
    
    @Update
    suspend fun updateAzanTime(azanTime: AzanTime)
    
    @Delete
    suspend fun deleteAzanTime(azanTime: AzanTime)
    
    @Query("DELETE FROM azan_times WHERE date = :date")
    suspend fun deleteAzanTimeByDate(date: String)
    
    @Query("DELETE FROM azan_times WHERE date < :date")
    suspend fun deleteOldAzanTimes(date: String)
    
    @Query("SELECT COUNT(*) FROM azan_times")
    suspend fun getAzanTimesCount(): Int
}

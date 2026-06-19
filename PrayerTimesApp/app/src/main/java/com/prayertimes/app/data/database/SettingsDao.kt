package com.prayertimes.app.data.database

import androidx.room.*
import androidx.lifecycle.LiveData
import com.prayertimes.app.data.models.PrayerSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    
    @Query("SELECT * FROM prayer_settings WHERE id = 1 LIMIT 1")
    suspend fun getSettings(): PrayerSettings?
    
    @Query("SELECT * FROM prayer_settings WHERE id = 1 LIMIT 1")
    fun getSettingsLiveData(): LiveData<PrayerSettings?>
    
    @Query("SELECT * FROM prayer_settings WHERE id = 1 LIMIT 1")
    fun getSettingsFlow(): Flow<PrayerSettings?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: PrayerSettings)
    
    @Update
    suspend fun updateSettings(settings: PrayerSettings)
    
    @Query("UPDATE prayer_settings SET enableSilentMode = :enabled WHERE id = 1")
    suspend fun updateSilentModeEnabled(enabled: Boolean)
    
    @Query("UPDATE prayer_settings SET silentModeDuration = :duration WHERE id = 1")
    suspend fun updateSilentModeDuration(duration: Int)
    
    @Query("UPDATE prayer_settings SET enableNotifications = :enabled WHERE id = 1")
    suspend fun updateNotificationsEnabled(enabled: Boolean)
    
    @Query("UPDATE prayer_settings SET enableAzanSound = :enabled WHERE id = 1")
    suspend fun updateAzanSoundEnabled(enabled: Boolean)
    
    @Query("UPDATE prayer_settings SET azanVolume = :volume WHERE id = 1")
    suspend fun updateAzanVolume(volume: Float)
    
    @Query("UPDATE prayer_settings SET selectedAzanSound = :soundName WHERE id = 1")
    suspend fun updateSelectedAzanSound(soundName: String)
    
    @Query("UPDATE prayer_settings SET enableVibration = :enabled WHERE id = 1")
    suspend fun updateVibrationEnabled(enabled: Boolean)
    
    @Query("UPDATE prayer_settings SET autoCalculateTimes = :enabled WHERE id = 1")
    suspend fun updateAutoCalculateEnabled(enabled: Boolean)
    
    @Query("UPDATE prayer_settings SET latitude = :latitude, longitude = :longitude WHERE id = 1")
    suspend fun updateLocation(latitude: Double, longitude: Double)
    
    @Query("UPDATE prayer_settings SET timezone = :timezone WHERE id = 1")
    suspend fun updateTimezone(timezone: String)
    
    @Query("UPDATE prayer_settings SET calculationMethod = :method WHERE id = 1")
    suspend fun updateCalculationMethod(method: String)
    
    @Query("DELETE FROM prayer_settings")
    suspend fun deleteAllSettings()
}

package com.prayertimes.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "prayer_times")
data class PrayerTime(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format: yyyy-MM-dd
    val fajr: String, // Format: HH:mm
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val isManuallySet: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
@Entity(tableName = "azan_times")
data class AzanTime(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format: yyyy-MM-dd
    val fajrAzan: String, // Format: HH:mm
    val dhuhrAzan: String,
    val asrAzan: String,
    val maghribAzan: String,
    val ishaAzan: String,
    val isManuallySet: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
@Entity(tableName = "islamic_events")
data class IslamicEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: String, // Format: yyyy-MM-dd
    val isImportant: Boolean = false,
    val eventType: EventType = EventType.GENERAL,
    val isRecurring: Boolean = false,
    val hijriDate: String? = null
) : Parcelable

enum class EventType {
    GENERAL,
    EID,
    RAMADAN,
    HAJJ,
    FRIDAY_PRAYER,
    LAYLAT_AL_QADR,
    ASHURA,
    MAWLID
}

@Parcelize
@Entity(tableName = "prayer_settings")
data class PrayerSettings(
    @PrimaryKey
    val id: Int = 1,
    val enableSilentMode: Boolean = true,
    val silentModeDuration: Int = 10, // minutes before prayer
    val enableNotifications: Boolean = true,
    val enableAzanSound: Boolean = true,
    val azanVolume: Float = 0.8f,
    val selectedAzanSound: String = "default",
    val enableVibration: Boolean = true,
    val autoCalculateTimes: Boolean = true,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val timezone: String = "",
    val calculationMethod: String = "ISNA" // Islamic Society of North America
) : Parcelable

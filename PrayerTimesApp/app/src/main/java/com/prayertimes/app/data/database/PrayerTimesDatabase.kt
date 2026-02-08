package com.prayertimes.app.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.prayertimes.app.data.models.PrayerTime
import com.prayertimes.app.data.models.AzanTime
import com.prayertimes.app.data.models.IslamicEvent
import com.prayertimes.app.data.models.PrayerSettings

@Database(
    entities = [
        PrayerTime::class,
        AzanTime::class,
        IslamicEvent::class,
        PrayerSettings::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PrayerTimesDatabase : RoomDatabase() {
    
    abstract fun prayerTimeDao(): PrayerTimeDao
    abstract fun azanTimeDao(): AzanTimeDao
    abstract fun islamicEventDao(): IslamicEventDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: PrayerTimesDatabase? = null

        fun getDatabase(context: Context): PrayerTimesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrayerTimesDatabase::class.java,
                    "prayer_times_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

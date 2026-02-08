package com.prayertimes.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.prayertimes.app.data.database.PrayerTimesDatabase

class PrayerTimesApplication : Application() {

    val database by lazy {
        Room.databaseBuilder(
            this,
            PrayerTimesDatabase::class.java,
            "prayer_times_database"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Prayer time notification channel
            val prayerChannel = NotificationChannel(
                PRAYER_CHANNEL_ID,
                "Prayer Times",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for prayer times"
                enableVibration(true)
                setShowBadge(true)
            }

            // Silent mode notification channel
            val silentChannel = NotificationChannel(
                SILENT_CHANNEL_ID,
                "Silent Mode",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications for silent mode activation"
                enableVibration(false)
                setShowBadge(false)
            }

            // Islamic events notification channel
            val eventsChannel = NotificationChannel(
                EVENTS_CHANNEL_ID,
                "Islamic Events",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for Islamic events and important dates"
                enableVibration(true)
                setShowBadge(true)
            }

            notificationManager.createNotificationChannel(prayerChannel)
            notificationManager.createNotificationChannel(silentChannel)
            notificationManager.createNotificationChannel(eventsChannel)
        }
    }

    companion object {
        const val PRAYER_CHANNEL_ID = "prayer_times_channel"
        const val SILENT_CHANNEL_ID = "silent_mode_channel"
        const val EVENTS_CHANNEL_ID = "islamic_events_channel"
    }
}

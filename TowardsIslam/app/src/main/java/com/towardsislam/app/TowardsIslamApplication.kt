package com.towardsislam.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.towardsislam.app.worker.PrayerTimeUpdateWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class TowardsIslamApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        scheduleDailyPrayerTimeUpdate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            // Prayer Time Notifications Channel
            val prayerChannel = NotificationChannel(
                "prayer_notifications",
                "Prayer Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for prayer times and Azan"
                enableVibration(true)
                setShowBadge(true)
            }

            // Reminder Notifications Channel
            val reminderChannel = NotificationChannel(
                "reminder_notifications",
                "Prayer Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminder notifications before prayer times"
                enableVibration(true)
            }

            // Silent Mode Channel
            val silentModeChannel = NotificationChannel(
                "silent_mode",
                "Silent Mode",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications for automatic silent mode"
            }

            // Service Notifications Channel
            val serviceChannel = NotificationChannel(
                "service_notifications",
                "Background Services",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background service notifications"
                setShowBadge(false)
            }

            notificationManager.createNotificationChannels(
                listOf(prayerChannel, reminderChannel, silentModeChannel, serviceChannel)
            )
        }
    }

    private fun scheduleDailyPrayerTimeUpdate() {
        val workRequest = PeriodicWorkRequestBuilder<PrayerTimeUpdateWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_prayer_time_update",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}


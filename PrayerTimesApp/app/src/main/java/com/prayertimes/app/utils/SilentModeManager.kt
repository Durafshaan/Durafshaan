package com.prayertimes.app.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import androidx.work.*
import com.prayertimes.app.data.models.PrayerSettings
import com.prayertimes.app.data.models.PrayerTime
import com.prayertimes.app.receivers.SilentModeReceiver
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object SilentModeManager {

    private const val SILENT_MODE_WORK_TAG = "silent_mode_work"
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun scheduleAllSilentModes(context: Context, prayerTime: PrayerTime, settings: PrayerSettings) {
        if (!settings.enableSilentMode) return

        val prayers = listOf(
            "Fajr" to prayerTime.fajr,
            "Dhuhr" to prayerTime.dhuhr,
            "Asr" to prayerTime.asr,
            "Maghrib" to prayerTime.maghrib,
            "Isha" to prayerTime.isha
        )

        prayers.forEach { (prayerName, prayerTimeStr) ->
            scheduleSilentMode(context, prayerName, prayerTimeStr, settings.silentModeDuration)
        }
    }

    private fun scheduleSilentMode(context: Context, prayerName: String, prayerTimeStr: String, durationMinutes: Int) {
        try {
            val prayerTime = timeFormat.parse(prayerTimeStr) ?: return
            val calendar = Calendar.getInstance()
            
            // Set prayer time for today
            val prayerCalendar = Calendar.getInstance().apply {
                time = prayerTime
                set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
            }

            // Calculate silent mode start time (X minutes before prayer)
            val silentStartTime = prayerCalendar.timeInMillis - (durationMinutes * 60 * 1000)
            val silentEndTime = prayerCalendar.timeInMillis + (5 * 60 * 1000) // End 5 minutes after prayer

            // Only schedule if the silent start time is in the future
            if (silentStartTime > System.currentTimeMillis()) {
                scheduleAlarm(context, silentStartTime, prayerName, true)
                scheduleAlarm(context, silentEndTime, prayerName, false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun scheduleAlarm(context: Context, triggerTime: Long, prayerName: String, enableSilent: Boolean) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        val intent = Intent(context, SilentModeReceiver::class.java).apply {
            putExtra("prayer_name", prayerName)
            putExtra("enable_silent", enableSilent)
        }

        val requestCode = (prayerName + enableSilent.toString()).hashCode()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            }
        } catch (e: SecurityException) {
            // Fallback to WorkManager if exact alarms are not allowed
            scheduleWithWorkManager(context, triggerTime, prayerName, enableSilent)
        }
    }

    private fun scheduleWithWorkManager(context: Context, triggerTime: Long, prayerName: String, enableSilent: Boolean) {
        val delay = triggerTime - System.currentTimeMillis()
        if (delay <= 0) return

        val workRequest = OneTimeWorkRequestBuilder<SilentModeWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(
                Data.Builder()
                    .putString("prayer_name", prayerName)
                    .putBoolean("enable_silent", enableSilent)
                    .build()
            )
            .addTag(SILENT_MODE_WORK_TAG)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    fun enableSilentMode(context: Context) {
        if (!PermissionHelper.hasDoNotDisturbPermission(context)) {
            return
        }

        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            if (notificationManager.isNotificationPolicyAccessGranted) {
                notificationManager.setInterruptionFilter(android.app.NotificationManager.INTERRUPTION_FILTER_NONE)
            }
        } else {
            audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
        }
    }

    fun disableSilentMode(context: Context) {
        if (!PermissionHelper.hasDoNotDisturbPermission(context)) {
            return
        }

        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            if (notificationManager.isNotificationPolicyAccessGranted) {
                notificationManager.setInterruptionFilter(android.app.NotificationManager.INTERRUPTION_FILTER_ALL)
            }
        } else {
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }
    }

    fun cancelAllSilentModeAlarms(context: Context) {
        // Cancel WorkManager tasks
        WorkManager.getInstance(context).cancelAllWorkByTag(SILENT_MODE_WORK_TAG)
        
        // Cancel AlarmManager alarms
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val prayers = listOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha")
        
        prayers.forEach { prayerName ->
            listOf(true, false).forEach { enableSilent ->
                val intent = Intent(context, SilentModeReceiver::class.java)
                val requestCode = (prayerName + enableSilent.toString()).hashCode()
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
                )
                pendingIntent?.let { alarmManager.cancel(it) }
            }
        }
    }
}

class SilentModeWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    
    override fun doWork(): Result {
        val prayerName = inputData.getString("prayer_name") ?: return Result.failure()
        val enableSilent = inputData.getBoolean("enable_silent", false)
        
        if (enableSilent) {
            SilentModeManager.enableSilentMode(applicationContext)
        } else {
            SilentModeManager.disableSilentMode(applicationContext)
        }
        
        return Result.success()
    }
}

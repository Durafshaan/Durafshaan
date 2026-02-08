package com.prayertimes.app.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.prayertimes.app.PrayerTimesApplication
import com.prayertimes.app.R
import com.prayertimes.app.ui.MainActivity

class PrayerAlarmReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra("prayer_name") ?: return
        val prayerTime = intent.getStringExtra("prayer_time") ?: return
        val isAzan = intent.getBooleanExtra("is_azan", false)
        
        showPrayerNotification(context, prayerName, prayerTime, isAzan)
    }
    
    private fun showPrayerNotification(context: Context, prayerName: String, prayerTime: String, isAzan: Boolean) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val title = if (isAzan) "Azan Time - $prayerName" else "Prayer Time - $prayerName"
        val message = if (isAzan) "It's time for $prayerName Azan ($prayerTime)" else "It's time for $prayerName prayer ($prayerTime)"
        
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val channelId = if (isAzan) PrayerTimesApplication.PRAYER_CHANNEL_ID else PrayerTimesApplication.PRAYER_CHANNEL_ID
        
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_mosque)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(0, 500, 250, 500))
            .build()
        
        val notificationId = (prayerName + isAzan.toString()).hashCode()
        notificationManager.notify(notificationId, notification)
    }
}

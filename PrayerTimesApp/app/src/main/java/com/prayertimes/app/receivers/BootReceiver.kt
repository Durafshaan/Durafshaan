package com.prayertimes.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.prayertimes.app.PrayerTimesApplication
import com.prayertimes.app.utils.SilentModeManager
import java.text.SimpleDateFormat
import java.util.*

class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || 
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED ||
            intent.action == Intent.ACTION_PACKAGE_REPLACED) {
            
            // Reschedule all alarms after boot
            rescheduleAlarms(context)
        }
    }
    
    private fun rescheduleAlarms(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val application = context.applicationContext as PrayerTimesApplication
                val database = application.database
                val settingsDao = database.settingsDao()
                val prayerTimeDao = database.prayerTimeDao()
                
                val settings = settingsDao.getSettings()
                if (settings?.enableSilentMode == true) {
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val todayPrayerTime = prayerTimeDao.getPrayerTimeByDate(today)
                    
                    todayPrayerTime?.let { prayerTime ->
                        SilentModeManager.scheduleAllSilentModes(context, prayerTime, settings)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

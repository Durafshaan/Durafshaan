package com.prayertimes.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.prayertimes.app.utils.SilentModeManager

class SilentModeReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra("prayer_name") ?: return
        val enableSilent = intent.getBooleanExtra("enable_silent", false)
        
        if (enableSilent) {
            SilentModeManager.enableSilentMode(context)
        } else {
            SilentModeManager.disableSilentMode(context)
        }
    }
}

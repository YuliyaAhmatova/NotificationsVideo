package com.example.notificationsvideo

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "DELETE_NOTIFICATION") {
            val notificationId = intent.getIntExtra("notification_id", -1)
            if (notificationId != -1) {
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.cancel(notificationId)
            }
        }
    }
}
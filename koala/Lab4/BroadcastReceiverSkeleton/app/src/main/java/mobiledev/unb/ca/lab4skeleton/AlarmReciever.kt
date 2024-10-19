package mobiledev.unb.ca.lab4skeleton

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.e(TAG, "Alarm received")
        val notificationHelper = NotificationHelper()
        notificationHelper.handleNotification(context)
    }
}
package com.c242ps070.turuku.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.c242ps070.turuku.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, i: Intent?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("alarm_channel", "Alarm Channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setContentTitle("Alarm is ringing!")
            .setContentText("Time to wake up!")
            .setSmallIcon(R.drawable.ic_alarm_on)
            .build()

        notificationManager.notify(1, notification)
    }
}
package com.c242ps070.turuku.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.c242ps070.turuku.R
import java.util.Calendar

object Alarm {
    fun setAlarm(context: Context, time: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val parts = time.split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            val i = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(i)
            return
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        // Show notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("alarm_channel", "Alarm Channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setContentTitle("Alarm Set")
            .setContentText("Alarm set for $time")
            .setSmallIcon(R.drawable.ic_alarm_on)
            .build()

        notificationManager.notify(1, notification)
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }

    fun isAlarmSet(context: Context): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        return pendingIntent != null
    }
}
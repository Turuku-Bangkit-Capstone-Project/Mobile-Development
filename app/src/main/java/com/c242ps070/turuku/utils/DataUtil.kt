package com.c242ps070.turuku.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getChronotypeName(chronotype: String): String {
    return when (chronotype) {
        "0" -> "Bear"
        "1" -> "Dolphin"
        "2" -> "Lion"
        "3" -> "Wolf"
        else -> "Unknown"
    }
}

fun convertTimeFormat(time: String): String {
    val parts = time.split(":")
    val hours = parts[0].toInt()
    val minutes = parts[1].toInt()

    return "$hours Hours, $minutes Minutes"
}

fun convertTimeLongToString(time: Long): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(time)
}

fun calculateSleepTime(recommendation: String): String {
    val parts = recommendation.split(":")
    val recommendedHours = parts[0].toInt()
    val recommendedMinutes = parts[1].toInt()

    val calendar = Calendar.getInstance().apply {
        add(get(Calendar.HOUR_OF_DAY), recommendedHours)
        add(Calendar.MINUTE, recommendedMinutes)
    }

    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(calendar.time)
}

fun timeToMillis(time: String): Long {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = format.parse(time) ?: return 0L

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, date.hours)
        set(Calendar.MINUTE, date.minutes)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return calendar.timeInMillis
}

fun getTodayAndYesterdayDate(): Pair<Date, Date> {
    val today = Calendar.getInstance().time
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    val yesterday = calendar.time
    return Pair(today, yesterday)
}

fun formatDateRange(start: Date, end: Date): String {
    val dateFormat = SimpleDateFormat("EEE, dd", Locale.getDefault())
    return "${dateFormat.format(start)} - ${dateFormat.format(end)}"
}
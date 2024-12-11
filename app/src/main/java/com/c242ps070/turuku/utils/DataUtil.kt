package com.c242ps070.turuku.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun getTimeHourAndMinute(time: String): List<Int> {
    val hour = time.split(":")[0].toInt()
    val minute = time.split(":")[1].toInt()
    return listOf(hour, minute)
}

fun getSleepDuration(bedTime: String, wakeupTime: String): Int {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())

    val bedTimeDate = format.parse(bedTime)
    val wakeupTimeDate = format.parse(wakeupTime)

    var duration = wakeupTimeDate.time - bedTimeDate.time
    if (duration < 0) {
        duration += 24 * 60 * 60 * 1000
    }

    return (duration / (60 * 60 * 1000)).toInt()
}

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
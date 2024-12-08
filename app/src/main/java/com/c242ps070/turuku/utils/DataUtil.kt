package com.c242ps070.turuku.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun getSleepHour(bedTime: String): Int {
    return bedTime.split(":")[0].toInt()
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
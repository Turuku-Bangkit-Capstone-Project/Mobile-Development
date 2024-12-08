package com.c242ps070.turuku.data.remote.request

import com.google.gson.annotations.SerializedName

data class HistoryRequest(
    @SerializedName("bedtime")
    val bedTime: String,

    @SerializedName("wakeuptime")
    val wakeupTime: String,

    @SerializedName("physical_activity_level")
    val physicalActivityLevel: Int,

    @SerializedName("daily_steps")
    val dailySteps: Int
)

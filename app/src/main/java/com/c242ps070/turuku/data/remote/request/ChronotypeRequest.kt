package com.c242ps070.turuku.data.remote.request

import com.google.gson.annotations.SerializedName

data class ChronotypeRequest(
    @SerializedName("bedtime_hour")
    val bedtimeHour: String,

    @SerializedName("wakeup_hour")
    val wakeupHour: String
)

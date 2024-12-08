package com.c242ps070.turuku.data.remote.request

import com.google.gson.annotations.SerializedName

data class ChronotypeRequest(
    @SerializedName("bedtime_hour")
    val bedtimeHour: Int,

    @SerializedName("wakeup_hour")
    val wakeupHour: Int
)

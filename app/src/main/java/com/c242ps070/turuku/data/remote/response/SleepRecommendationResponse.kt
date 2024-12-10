package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class SleepRecommendationResponse(
    @SerializedName("msg")
    val message: String,

    @SerializedName("recommended_sleep_duration")
    val recommendSleepDuration: String
)

package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class SleepRecommendationResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("sleep_duration")
    val sleepDuration: String,

    @SerializedName("recommended_sleep_duration")
    val recommendSleepDuration: String
)

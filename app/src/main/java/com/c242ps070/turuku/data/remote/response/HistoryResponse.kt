package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("id_user")
    val userId: Int,

    @SerializedName("bedtime")
    val bedTime: String,

    @SerializedName("wakeuptime")
    val wakeupTime: String,

    @SerializedName("sleep_recommendation")
    val sleepRecommendation: String? = null,

    @SerializedName("physical_activity_level")
    val physicalActivityLevel: Int,

    @SerializedName("daily_steps")
    val dailySteps: Int,

    @SerializedName("created_at")
    val createdAt: String? = null
)

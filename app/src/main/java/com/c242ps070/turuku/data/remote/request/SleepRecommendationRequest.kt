package com.c242ps070.turuku.data.remote.request

import com.google.gson.annotations.SerializedName

data class SleepRecommendationRequest(
    @SerializedName("daily_steps")
    val dailySteps: Int,

    @SerializedName("physical_activity_level")
    val physicalActivityLevel: Int,

    @SerializedName("age")
    val age: Int,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("chronotype")
    val chronotype: Int
)

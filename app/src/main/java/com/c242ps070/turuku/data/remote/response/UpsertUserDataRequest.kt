package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpsertUserDataRequest(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("age")
    val age: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("bedTime")
    val bedTime: String? = null,

    @SerializedName("wakeupTime")
    val wakeupTime: String? = null
)

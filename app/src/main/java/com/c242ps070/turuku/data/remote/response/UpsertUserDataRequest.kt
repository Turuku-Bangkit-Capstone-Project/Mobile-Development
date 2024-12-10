package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpsertUserDataRequest(
    @SerializedName("age")
    val age: Int? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("chronotype")
    val chronotype: String? = null
)

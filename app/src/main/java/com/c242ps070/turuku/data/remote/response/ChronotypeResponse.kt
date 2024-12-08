package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChronotypeResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("chronotype")
    val chronotype: String
)

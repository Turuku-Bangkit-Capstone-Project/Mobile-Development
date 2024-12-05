package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("msg")
    val message: String? = null
)

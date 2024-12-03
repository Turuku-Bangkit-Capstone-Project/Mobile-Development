package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("msg")
    val message: String,
)

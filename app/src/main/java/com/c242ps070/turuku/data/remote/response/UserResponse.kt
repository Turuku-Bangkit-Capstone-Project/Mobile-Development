package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("name")
    val name: String
)

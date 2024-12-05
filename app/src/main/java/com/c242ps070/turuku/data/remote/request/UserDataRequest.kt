package com.c242ps070.turuku.data.remote.request

import com.google.gson.annotations.SerializedName

data class UserDataRequest(
    @SerializedName("userId")
    val userId: Int
)
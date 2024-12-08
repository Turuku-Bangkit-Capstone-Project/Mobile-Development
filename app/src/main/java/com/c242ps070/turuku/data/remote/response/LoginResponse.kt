package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String
)

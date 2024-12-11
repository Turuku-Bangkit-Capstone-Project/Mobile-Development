package com.c242ps070.turuku.data.remote.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("currentPassword")
    val currentPassword: String,

    @SerializedName("newPassword")
    val newPassword: String,

    @SerializedName("confNewPassword")
    val confNewPassword: String
)
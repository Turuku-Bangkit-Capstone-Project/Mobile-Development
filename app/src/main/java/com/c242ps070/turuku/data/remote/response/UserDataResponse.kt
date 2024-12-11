package com.c242ps070.turuku.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDataResponse(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("age")
    val age: Int? = null,

    @SerializedName("gender")
    val gender: Int? = null,

    @SerializedName("chronotype")
    val chronotype: String? = null
)
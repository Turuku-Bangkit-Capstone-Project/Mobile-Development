package com.c242ps070.turuku.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ChangePasswordResponse(

	@field:SerializedName("confNewPassword")
	val confNewPassword: String? = null,

	@field:SerializedName("newPassword")
	val newPassword: String? = null,

	@field:SerializedName("currentPassword")
	val currentPassword: String? = null
) : Parcelable

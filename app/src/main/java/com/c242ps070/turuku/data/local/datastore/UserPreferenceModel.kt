package com.c242ps070.turuku.data.local.datastore

data class UserPreferenceModel(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val token: String,
    val age: Int? = null,
    val gender: String? = null,
    val bedTime: String? = null,
    val wakeupTime: String? = null,
    val physicalActivity: Int? = null,
    val dailySteps: Int? = null,
    val chronotype: String? = null
)

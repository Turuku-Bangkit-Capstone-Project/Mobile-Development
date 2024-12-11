package com.c242ps070.turuku.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "sleep_history")
data class SleepHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("start_time")
    val startTime: String,

    @ColumnInfo("end_time")
    val endTime: String,

    @ColumnInfo("sleep_recommendation")
    val sleepRecommendation: String,

    @ColumnInfo("physical_activity_level")
    val physicalActivityLevel: Int,

    @ColumnInfo("daily_steps")
    val dailySteps: Int,

    @ColumnInfo("created_at")
    val createdAt: Date = Date()
)

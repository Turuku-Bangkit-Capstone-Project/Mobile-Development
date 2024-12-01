package com.c242ps070.turuku.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_history")
data class SleepHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("start_time")
    val startTime: String,

    @ColumnInfo("end_time")
    val endTime: String,

    @ColumnInfo("sleep_duration")
    val sleepDuration: Int, // in milliseconds

    @ColumnInfo("created_at")
    val createdAt: String
)

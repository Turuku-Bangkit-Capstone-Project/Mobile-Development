package com.c242ps070.turuku.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c242ps070.turuku.data.local.room.entity.SleepHistoryEntity

@Dao
interface SleepHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSleepHistory(sleepHistory: SleepHistoryEntity)

    @Query("SELECT * FROM sleep_history ORDER BY id DESC LIMIT 1")
    suspend fun getLastSleepHistory(): SleepHistoryEntity?

    @Query("SELECT * FROM sleep_history")
    suspend fun getAllSleepHistory(): List<SleepHistoryEntity>

    @Query("DELETE FROM sleep_history")
    suspend fun deleteAllSleepHistory()
}
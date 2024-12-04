package com.c242ps070.turuku.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c242ps070.turuku.data.local.room.dao.SleepHistoryDao
import com.c242ps070.turuku.data.local.room.entity.SleepHistoryEntity

@Database(
    entities = [SleepHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TurukuDatabase: RoomDatabase() {
    abstract fun sleepHistoryDao(): SleepHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: TurukuDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TurukuDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TurukuDatabase::class.java, "turuku_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
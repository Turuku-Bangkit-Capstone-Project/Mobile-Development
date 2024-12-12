package com.c242ps070.turuku.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.alarmDataStore by preferencesDataStore(name = "alarm")

class AlarmPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val WAKEUP_TIME = longPreferencesKey(ALARM_WAKEUP_TIME)
    private val BED_TIME = longPreferencesKey(ALARM_BED_TIME)

    val alarmTime: Flow<Pair<Long?, Long?>> = dataStore.data
        .map { preferences ->
            val wakeupTime = preferences[WAKEUP_TIME]
            val bedTime = preferences[BED_TIME]
            Pair(wakeupTime, bedTime)
        }

    suspend fun saveAlarmTime(alarmTime: Long, bedTime: Long) {
        dataStore.edit { preferences ->
            preferences[WAKEUP_TIME] = alarmTime
            preferences[BED_TIME] = bedTime
        }
    }

    suspend fun remove() {
        dataStore.edit { preferences ->
            preferences.remove(WAKEUP_TIME)
            preferences.remove(BED_TIME)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AlarmPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): AlarmPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = AlarmPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }

        const val ALARM_WAKEUP_TIME = "alarm_wakeup_time"
        const val ALARM_BED_TIME = "alarm_bed_time"
    }
}
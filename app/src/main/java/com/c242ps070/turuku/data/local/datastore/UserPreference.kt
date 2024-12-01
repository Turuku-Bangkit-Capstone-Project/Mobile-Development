package com.c242ps070.turuku.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val USER_ID = stringPreferencesKey(ID)
    private val USER_NAME = stringPreferencesKey(NAME)
    private val USER_EMAIL = stringPreferencesKey(EMAIL)
    private val USER_TOKEN = stringPreferencesKey(TOKEN)

    fun getUser(): Flow<UserPreferenceModel> {
        return dataStore.data.map { preferences ->
            UserPreferenceModel(
                id = preferences[USER_ID] ?: "",
                name = preferences[USER_NAME] ?: "",
                email = preferences[USER_EMAIL] ?: "",
                token = preferences[USER_TOKEN] ?: ""
            )
        }
    }

    suspend fun saveUser(model: UserPreferenceModel) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = model.id
            preferences[USER_NAME] = model.name
            preferences[USER_EMAIL] = model.email
            preferences[USER_TOKEN] = model.token
        }
    }

    suspend fun delete() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }

        const val ID = "id"
        const val NAME = "name"
        const val EMAIL = "email"
        const val TOKEN = "token"
    }
}
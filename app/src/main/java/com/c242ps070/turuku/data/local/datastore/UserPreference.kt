package com.c242ps070.turuku.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.c242ps070.turuku.utils.AppAuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val USER_ID = intPreferencesKey(ID)
    private val USER_NAME = stringPreferencesKey(NAME)
    private val USER_EMAIL = stringPreferencesKey(EMAIL)
    private val USER_TOKEN = stringPreferencesKey(TOKEN)
    private val USER_AGE = intPreferencesKey(AGE)
    private val USER_GENDER = stringPreferencesKey(GENDER)
    private val USER_BEDTIME = stringPreferencesKey(BEDTIME)
    private val USER_WAKEUPTIME = stringPreferencesKey(WAKEUPTIME)
    private val USER_PHYSICAL_ACTIVITY = intPreferencesKey(PHYSICAL_ACTIVITY)
    private val USER_DAILY_STEPS = intPreferencesKey(DAILY_STEPS)
    private val USER_CHRONOTYPE = stringPreferencesKey(CHRONOTYPE)
    private val USER_REFRESH_TOKEN = stringPreferencesKey(REFRESH_TOKEN)
    private val USER_APP_AUTH_STATE = stringPreferencesKey(APP_AUTH_STATE)

    fun getUser(): Flow<UserPreferenceModel> {
        return dataStore.data.map { preferences ->
            UserPreferenceModel(
                id = preferences[USER_ID],
                name = preferences[USER_NAME],
                email = preferences[USER_EMAIL],
                token = preferences[USER_TOKEN] ?: "",
                age = preferences[USER_AGE],
                gender = preferences[USER_GENDER],
                bedTime = preferences[USER_BEDTIME],
                wakeupTime = preferences[USER_WAKEUPTIME],
                physicalActivity = preferences[USER_PHYSICAL_ACTIVITY],
                dailySteps = preferences[USER_DAILY_STEPS],
                chronotype = preferences[USER_CHRONOTYPE]
            )
        }
    }

    val refreshToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[USER_REFRESH_TOKEN]
        }

    val appAuthState: Flow<AppAuthState>
        get() = dataStore.data.map { preferences ->
            AppAuthState.valueOf(preferences[USER_APP_AUTH_STATE] ?: AppAuthState.IS_NOT_LOGGED_IN.name)
        }

    suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[USER_REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun saveUser(model: UserPreferenceModel) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = model.id ?: -1
            preferences[USER_NAME] = model.name ?: ""
            preferences[USER_EMAIL] = model.email ?: ""
            preferences[USER_TOKEN] = model.token
        }
    }

    suspend fun saveAgeAndGender(
        age: Int,
        gender: String
    ) {
        dataStore.edit { preferences ->
            preferences[USER_AGE] = age
            preferences[USER_GENDER] = gender
        }
    }

    suspend fun saveBedTime(
        bedTime: String
    ) {
        dataStore.edit { preferences ->
            preferences[USER_BEDTIME] = bedTime
        }
    }

    suspend fun saveWakeupTime(
        wakeupTime: String
    ) {
        dataStore.edit { preferences ->
            preferences[USER_WAKEUPTIME] = wakeupTime
        }
    }

    suspend fun savePhysicalActivityAndDailySteps(
        physicalActivity: Int,
        dailySteps: Int
    ) {
        dataStore.edit { preferences ->
            preferences[USER_PHYSICAL_ACTIVITY] = physicalActivity
            preferences[USER_DAILY_STEPS] = dailySteps
        }
    }

    suspend fun saveChronotype(chronotype: String) {
        dataStore.edit { preferences ->
            preferences[USER_CHRONOTYPE] = chronotype
        }
    }

    suspend fun saveAppAuthState(state: AppAuthState) {
        dataStore.edit { preferences ->
            preferences[USER_APP_AUTH_STATE] = state.name
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
        const val AGE = "age"
        const val GENDER = "gender"
        const val BEDTIME = "bedTime"
        const val WAKEUPTIME = "wakeup_time"
        const val PHYSICAL_ACTIVITY = "physical_activity"
        const val DAILY_STEPS = "daily_steps"
        const val CHRONOTYPE = "chronotype"
        const val REFRESH_TOKEN = "refresh_token"
        const val APP_AUTH_STATE = "app_auth_state"
    }
}
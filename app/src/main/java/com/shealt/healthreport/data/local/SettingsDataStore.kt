package com.shealt.healthreport.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val dataStore: DataStore<Preferences>) {

    companion object {
        val REPORT_HOUR = intPreferencesKey("report_hour")
        val REPORT_MINUTE = intPreferencesKey("report_minute")
        val AUTO_REPORT_ENABLED = booleanPreferencesKey("auto_report_enabled")
    }

    val reportHourFlow: Flow<Int> = dataStore.data.map { preferences ->
        preferences[REPORT_HOUR] ?: 23 // Default to 11 PM
    }

    val reportMinuteFlow: Flow<Int> = dataStore.data.map { preferences ->
        preferences[REPORT_MINUTE] ?: 0 // Default to 0 minutes
    }

    val autoReportEnabledFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[AUTO_REPORT_ENABLED] ?: true // Default to enabled
    }

    suspend fun setReportTime(hour: Int, minute: Int) {
        dataStore.edit { preferences ->
            preferences[REPORT_HOUR] = hour
            preferences[REPORT_MINUTE] = minute
        }
    }

    suspend fun setAutoReportEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTO_REPORT_ENABLED] = enabled
        }
    }
}

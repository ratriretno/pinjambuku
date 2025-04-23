package com.example.pinjambuku.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val LOGIN_KEY = booleanPreferencesKey("login_setting")
    private val USER_ID_KEY = stringPreferencesKey("user_id")

    fun getLoginSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[LOGIN_KEY] ?: false
        }
    }

    fun getUserSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }
    }

    suspend fun getLogin(): Boolean {
        return dataStore.data.first()[LOGIN_KEY] ?: false
    }

    suspend fun getUserId(): String{
        return dataStore.data.first()[USER_ID_KEY] ?: ""
    }

    suspend fun saveLogin(isLogin: Boolean, id : String) {
        dataStore.edit { preferences ->
            preferences[LOGIN_KEY] = isLogin
        }
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}
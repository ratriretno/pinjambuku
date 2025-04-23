package com.example.pinjambuku.network

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

object Constant {
//    val datastore: DataStore<Preferences> = Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    //status event value
    const val UPCOMING = 1
    const val ALL = 0
    const val PAST = -1

    const val BASE_URL = "https://pinjambuku.solfagaming.com"

   const val PREFERENCES_SETTING = "setting"
    val Context.dataStore by preferencesDataStore(
        name = PREFERENCES_SETTING
    )
}
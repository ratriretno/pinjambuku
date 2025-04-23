package com.example.pinjambuku.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.pinjambuku.network.ApiConfig
import com.example.pinjambuku.data.SettingPreferences
import com.example.pinjambuku.database.ExampleBookRoomDatabase
import com.example.pinjambuku.repository.BookRepository
import com.example.pinjambuku.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context, dataStore: DataStore<Preferences>): BookRepository {
        val apiService = ApiConfig.getApiService()
        val database = ExampleBookRoomDatabase.getInstance(context)
        val bookBorrowDao = database.exampleBorrowedBookDao()
        val bookFavoriteDao= database.favoriteBookDao()
        val appExecutors = AppExecutors()
        val pref= SettingPreferences.getInstance(dataStore)
        return BookRepository.getInstance(apiService, bookBorrowDao, bookFavoriteDao, appExecutors, pref)
    }
}
package com.example.pinjambuku.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pinjambuku.BookViewModel
import com.example.pinjambuku.repository.BookRepository
import com.example.pinjambuku.ui.screen.HomeViewModel
import com.example.pinjambuku.ui.screen.LoginViewModel


class ViewModelFactory private constructor(private val repository: BookRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }  else if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(repository) as T}
        else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T}
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context, dataStore: DataStore<Preferences>): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context, dataStore))
            }.also { instance = it }
    }
}
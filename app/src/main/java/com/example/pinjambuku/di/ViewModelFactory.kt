package com.example.pinjambuku.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pinjambuku.BookViewModel
import com.example.pinjambuku.repository.BookRepository
import com.example.pinjambuku.ui.screen.DetailViewModel
import com.example.pinjambuku.ui.screen.HomeViewModel
import com.example.pinjambuku.ui.screen.LoginViewModel
import com.example.pinjambuku.ui.screen.ProfileViewModel
import com.example.pinjambuku.ui.screen.SignupViewModel


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
        else if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(repository) as T}
        else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T}
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T}
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
package com.example.pinjambuku.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.model.BorrowResponse
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: BookRepository) : ViewModel() {
    // Borrowed book state (default false)
    var isBorrowed = mutableStateOf(false)
        private set

    // Favorite state (default false)
    var isFavorite = mutableStateOf(false)
        private set

    private val _result = MutableLiveData<ResultNetwork<BorrowResponse>>()
    val result: LiveData<ResultNetwork<BorrowResponse>> = _result

    //loading first page
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _book = MutableStateFlow(BookModel())
    val book: StateFlow<BookModel> = _book

    private val _isLogin = MutableStateFlow(true)
    val isLogin: StateFlow<Boolean> = _isLogin

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId

    fun setBook (bookModel: BookModel){
        _book.value=bookModel
    }

    fun updateBook(idUser: String){
        _book.value.available=false
        _book.value.idBorrower=idUser
    }
    fun getLogin () : LiveData<Boolean> {
        return repository.getLoginSetting()
    }

    fun getUserIdSetting () : LiveData<String> {
        return repository.getUserSetting()
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun logout() {
        viewModelScope.launch {
            repository.saveLoginSetting(false, "")
        }
    }

    fun borrowBook (idBook: String, idUser : String, bookName : String){
        setLoading(true)
        viewModelScope.launch {
           _result.value= repository.borrow(idBook, idUser, bookName)
        }
    }

    fun returnBook (idBook: String, idTransaksi: String, bookName : String){
        setLoading(true)
        Log.i("model", idTransaksi)
        viewModelScope.launch {
            _result.value= repository.returnBook(idBook, idTransaksi, bookName)
        }
    }

}
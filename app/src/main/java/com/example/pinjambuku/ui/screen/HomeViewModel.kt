package com.example.pinjambuku.ui.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.repository.BookRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(private val repository: BookRepository) : ViewModel() {
    private val _result = MutableLiveData<ResultNetwork<List<BookModel>>>()
    val result: LiveData<ResultNetwork<List<BookModel>>> = _result

    private val _books = MutableStateFlow<MutableList<BookModel>>(mutableListOf())
    val bookList: StateFlow<List<BookModel>>
        get() = _books.asStateFlow()

    private val _booksAllFirstLoad = MutableStateFlow<MutableList<BookModel>>(mutableListOf())
    val bookListAllFirstLoad: StateFlow<List<BookModel>>
        get() = _booksAllFirstLoad.asStateFlow()

    //loading first page
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    companion object {
        private const val TAG = "UpcomingFragmentViewModel"
    }

    init {
        viewModelScope.launch {
            getUpcoming()
        }
    }

    private suspend fun getUpcoming() {
        _result.value = repository.getBooks()
    }

    fun setBooks(books: List<BookModel>) {
        if (_booksAllFirstLoad.value.isEmpty()) {
            _booksAllFirstLoad.value.addAll(books)
        }
        _books.value.clear()
        _books.value.addAll(books)
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun search() {
        _isLoading.value = true
        viewModelScope.launch {
            _result.value = repository.searchBooks(_query.value)
        }
    }

    fun setQuery(query: String) {
        _query.value = query
        if (_query.value.isEmpty()) {
            _isLoading.value = true
            resetList()
            _isLoading.value = false
        }
    }

    fun resetList() {
        _isLoading.value = true
        viewModelScope.launch {
            getUpcoming()
        }
        Log.i("reset", _isLoading.value.toString())
        Log.i("reset", _books.value.toString())
    }

    fun setBookFirstLoad(){

    }
}
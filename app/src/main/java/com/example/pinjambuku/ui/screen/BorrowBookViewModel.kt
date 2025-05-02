package com.example.pinjambuku.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.model.BooksResponse
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BorrowBookViewModel (private val repository: BookRepository) : ViewModel() {
    private val _resultBorrow = MutableLiveData<ResultNetwork<BooksResponse>>()
    val resultBorrow: LiveData<ResultNetwork<BooksResponse>> = _resultBorrow

    private val _books = MutableStateFlow<MutableList<BookModel>>(mutableListOf())
    val bookList: StateFlow<List<BookModel>>
        get() = _books.asStateFlow()

    //loading first page
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    companion object {
        private const val TAG = "UpcomingFragmentViewModel"
    }

  fun listBorrowBook(idUser : String) {
        _isLoading.value = true
        viewModelScope.launch {
            _resultBorrow.value = repository.listBorrowBooks(idUser)
        }
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setBooks(books: List<BookModel>) {
        _books.value.clear()
        _books.value.addAll(books)
    }

}
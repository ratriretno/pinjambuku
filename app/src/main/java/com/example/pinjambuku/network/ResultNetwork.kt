package com.example.pinjambuku.network

sealed class ResultNetwork<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultNetwork<T>()
    data class Error(val error: String) : ResultNetwork<Nothing>()
    data object Loading : ResultNetwork<Nothing>()
}
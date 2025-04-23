package com.example.pinjambuku.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("books.php")
        suspend fun getAllBooks(): BooksResponse

    @GET("SearchBooks.php")
    suspend fun searchBooks(@Query("keyword") keyword : String): BooksResponse
//
//    @GET("events")
//    fun getEvent(@Query("active") status : Int ): Call<EventsResponse>
//
//    @GET("events")
//    suspend fun getEvents(@Query("active") status : Int ): Call<EventsResponse>
}
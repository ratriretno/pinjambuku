package com.example.pinjambuku.network

import retrofit2.http.GET

interface ApiService {

    @GET("books.php")
        suspend fun getAllBooks(): BooksResponse
//
//    @GET("events")
//    fun getEvent(@Query("active") status : Int ): Call<EventsResponse>
//
//    @GET("events")
//    suspend fun getEvents(@Query("active") status : Int ): Call<EventsResponse>
}
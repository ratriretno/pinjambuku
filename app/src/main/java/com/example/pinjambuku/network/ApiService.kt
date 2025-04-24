package com.example.pinjambuku.network

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("books.php")
        suspend fun getAllBooks(): BooksResponse

    @GET("SearchBooks.php")
    suspend fun searchBooks(@Query("keyword") keyword : String): BooksResponse

    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(@Field("email") email: String, @Field("password") password : String): LoginResponse


}
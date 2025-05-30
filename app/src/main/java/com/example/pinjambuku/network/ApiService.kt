package com.example.pinjambuku.network

import com.example.pinjambuku.model.BooksResponse
import com.example.pinjambuku.model.BorrowResponse
import com.example.pinjambuku.model.LoginResponse
import com.example.pinjambuku.model.ProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("books.php")
        suspend fun getAllBooks(): BooksResponse

    @GET("SearchBooks.php")
    suspend fun searchBooks(@Query("keyword") keyword : String): BooksResponse

    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(@Field("email") email: String, @Field("password") password : String): LoginResponse

    @FormUrlEncoded
    @POST("signUp.php")
    suspend fun signup(
        @Field("email") email: String,
        @Field("password") password : String,
        @Field("fullname") fullname : String,
        @Field("username") username : String,
        @Field("city") city : String
        ): LoginResponse

    @FormUrlEncoded
    @POST("getProfile.php")
    suspend fun profile(@Field("idUser") id : String) : ProfileResponse

    @FormUrlEncoded
    @POST("borrowBook.php")
    suspend fun borrowBook(@Field("idBuku") idBook: String, @Field("idUser") idUser : String,
                           @Field("name") bookName : String): BorrowResponse

    @FormUrlEncoded
    @POST("returnBook.php")
    suspend fun returnBook(@Field("idBuku") idBook: String, @Field("idTransaksi") idTransaksi : String): BorrowResponse


    @GET("listBorrowBook.php")
    suspend fun listBorrowBook( @Query("id_user") idUser : String): BooksResponse
}
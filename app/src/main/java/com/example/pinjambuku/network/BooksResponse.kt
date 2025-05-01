package com.example.pinjambuku.network

import com.example.pinjambuku.model.BookModel
import com.google.gson.annotations.SerializedName

data class BooksResponse (
    @field:SerializedName("error"      ) var error      : Boolean,
    @field:SerializedName("message"    ) var message    : String,
    @field:SerializedName("listBooks" ) var listBooks : List<BookModel>
)


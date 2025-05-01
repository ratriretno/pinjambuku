package com.example.pinjambuku.network

import com.google.gson.annotations.SerializedName

data class BorrowResponse (
    @field:SerializedName("error"      ) var error      : Boolean,
    @field:SerializedName("message"    ) var message    : String,
)
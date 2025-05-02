package com.example.pinjambuku.model

import com.google.gson.annotations.SerializedName

data class BorrowResponse (
    @field:SerializedName("error"      ) var error      : Boolean,
    @field:SerializedName("message"    ) var message    : String,
)